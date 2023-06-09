package com.example.storyapp.ui.story.ui.story_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.preference.UserModel
import com.example.storyapp.repository.UserStoryRepository
import com.example.storyapp.response.StoryResponse
import kotlinx.coroutines.launch

class StoryFragmentViewModel(private val repo: UserStoryRepository) : ViewModel() {
    val storyList: LiveData<StoryResponse> = repo.liveStoryList()
    val isLoading: LiveData<Boolean> = repo.liveLoad()

    fun stateSaverUser(): LiveData<UserModel>{
        return repo.getUser()
    }

    fun getStories(token: String){
        viewModelScope.launch { repo.getStoryList(token) }
    }
}