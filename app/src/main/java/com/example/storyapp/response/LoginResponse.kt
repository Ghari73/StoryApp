package com.example.storyapp.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result : LoginResultResponse? = null
)

data class LoginResultResponse(
    @field:SerializedName("userId")
    val userId : String,

    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("token")
    val token : String
)
