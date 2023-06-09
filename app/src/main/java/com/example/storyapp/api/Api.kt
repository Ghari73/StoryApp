package com.example.storyapp.api

import com.example.storyapp.response.AddStoryResponse
import com.example.storyapp.response.LoginResponse
import com.example.storyapp.response.RegResponse
import com.example.storyapp.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email: String,
        @Field("password") password : String
    ): Call<RegResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun AddStory(
        @Part file : MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<AddStoryResponse>

    @Multipart
    @POST("stories/guest")
    fun AddStoryAsGuest(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<AddStoryResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String
    ) : Call<StoryResponse>
}