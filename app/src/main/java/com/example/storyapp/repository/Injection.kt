package com.example.storyapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.preference.AuthPreference

private val Context.data : DataStore<Preferences> by preferencesDataStore("TOKEN")

object Injection {
    fun provideRepository(context: Context): UserStoryRepository {
        val apiService = ApiConfig.getApiService()
        val pref = AuthPreference.getInstance(context.data)
        return UserStoryRepository.getInstance(pref, apiService)
    }
}