package com.example.storyapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.preference.UserModel
import com.example.storyapp.repository.UserStoryRepository
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.RegResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val repo : UserStoryRepository) : ViewModel() {
    val register : LiveData<RegResponse> = repo.liveReg()
    val login : LiveData<LoginResponse> = repo.liveLogin()
    val isLoading : LiveData<Boolean> = repo.liveLoad()

    fun regPostData(name: String, email: String, pass: String){
        viewModelScope.launch { repo.register(name, email, pass) }
    }

    fun loginPostData(email: String, pass: String){
        viewModelScope.launch { repo.login(email,pass) }
    }

    fun userLogin(){
        viewModelScope.launch { repo.userLogin() }
    }

    fun userLogout(){
        viewModelScope.launch { repo.userLogout() }
    }

    fun saveStateUser(model : UserModel){
        viewModelScope.launch { repo.stateSaverUser(model) }
    }
}