package com.example.storyapp.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.repository.Injection
import com.example.storyapp.repository.UserStoryRepository
import com.example.storyapp.ui.story.ui.story_list.StoryFragmentViewModel

class ViewModelFactory(
    private val authRepo : UserStoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->{
                AuthViewModel(authRepo) as T
            }
            modelClass.isAssignableFrom(StoryFragmentViewModel::class.java) ->{
                StoryFragmentViewModel(authRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            context: Context
        ): ViewModelFactory = instance ?: synchronized(this){
            instance?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }

    }
}