package com.example.storyapp.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AddStoryResponse(
    val error: Boolean,
    val message: String
)

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<ListStory>
)

@Parcelize
data class ListStory(
    val id : String,
    val name : String,
    val description : String,
    val photoUrl : String,
    val createdAt : String,
    val lat : Double,
    val lon : Double,
): Parcelable

