package com.example.storyapp.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AuthPreference (private val data : DataStore<Preferences>){

    suspend fun stateSaverUser(newUser : UserModel){
        withContext(Dispatchers.IO){
            data.edit {
                pref ->
                pref[KEY_NAME] = newUser.name
                pref[KEY_TOKEN] = newUser.token
                pref[KEY_LOGIN_STATE] = newUser.stateLogin
            }
        }
    }

    suspend fun loginCheck(isLoggedIn : Boolean){
        if (isLoggedIn){
            data.edit { pref -> pref[KEY_LOGIN_STATE] = true }
        }else{
            data.edit { it.clear() }
        }
    }

    fun getUserData(): Flow<UserModel> {
        return data.data.map {
            pref ->
            val name = pref[KEY_NAME] ?: ""
            val token = pref[KEY_TOKEN] ?: ""
            val stateLogin = pref[KEY_LOGIN_STATE] ?: false
            UserModel(name, token, stateLogin)
        }
    }

    companion object{
        @Volatile
        private var instance : AuthPreference? = null
        private val KEY_NAME = stringPreferencesKey("NAME")
        private val KEY_TOKEN = stringPreferencesKey("TOKEN")
        private val KEY_LOGIN_STATE = booleanPreferencesKey("LOGGED STATE")
        fun getInstance(
            data : DataStore<Preferences>)
        : AuthPreference = instance?: synchronized(this){
            val _instance = AuthPreference(data)
            instance = _instance
            _instance
        }
    }
}