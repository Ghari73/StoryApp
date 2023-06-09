package com.example.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.storyapp.api.Api
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.preference.AuthPreference
import com.example.storyapp.preference.UserModel
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.RegResponse
import com.example.storyapp.response.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserStoryRepository(private val pref: AuthPreference, private val api : Api) {
    private val reg = MutableLiveData<RegResponse>()
    private val login = MutableLiveData<LoginResponse>()
    private val isLoading = MutableLiveData<Boolean>()
    private val storyList = MutableLiveData<StoryResponse>()

    fun liveLoad() : LiveData<Boolean> = isLoading
    fun liveReg() : LiveData<RegResponse> = reg
    fun liveLogin() : LiveData<LoginResponse> = login
    fun liveStoryList(): LiveData<StoryResponse> = storyList

    fun register(name : String, email : String, pass : String){
        isLoading.value = true
        val client = ApiConfig.getApiService().register(name,email,pass)
        client.enqueue(object : Callback<RegResponse> {
            override fun onResponse(
                call : Call<RegResponse>,
                res : Response<RegResponse>
            ){
                isLoading.value = false
                if (res.isSuccessful){
                    reg.value = res.body()
                }else {
                    Log.d("Fail in reg repo",res.message())
                }
            }

            override fun onFailure(call: Call<RegResponse>, t: Throwable) {
                isLoading.value = false
                Log.d("Fail in reg repo",t.message.toString())
                t.printStackTrace()
            }
        })
    }

    fun login(email : String, pass : String){
        isLoading.value = true
        val client = ApiConfig.getApiService().login(email,pass)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call : Call<LoginResponse>,
                res : Response<LoginResponse>
            ){
                isLoading.value = false
                if (res.isSuccessful){
                    login.value = res.body()
                }else {
                    Log.d("Fail in login repo",res.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.value = false
                Log.d("Fail in login repo",t.message.toString())
                t.printStackTrace()
            }
        })
    }

    suspend fun stateSaverUser(model : UserModel){
        pref.stateSaverUser(model)
    }
    suspend fun userLogin(){
        pref.loginCheck(true)
    }

    suspend fun userLogout(){
        pref.loginCheck(false)
    }

    fun getUser(): LiveData<UserModel>{
        return pref.getUserData().asLiveData()
    }

    fun getStoryList(token : String){
        isLoading.value = true
        val client = ApiConfig.getApiService().getAllStories(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call : Call<StoryResponse>,
                res : Response<StoryResponse>
            ){
                isLoading.value = false
                if (res.isSuccessful){
                    storyList.value = res.body()
                }else {
                    Log.d("Fail on GetStoryList",res.message())
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                isLoading.value = false
                Log.d("Fail on GetStoryList",t.message.toString())
                t.printStackTrace()
            }
        })
    }

    companion object{
        @Volatile
        private var instance: UserStoryRepository? = null
        fun getInstance(
            pref: AuthPreference,
            api: Api
        ): UserStoryRepository =
            instance ?: synchronized(this) {
                instance ?: UserStoryRepository(pref, api)
            }.also {
                instance = it
            }
    }
}