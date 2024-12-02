package com.example.shara.data.di

import android.content.Context
import com.example.shara.data.Repository
import com.example.shara.data.api.ApiConfig
import com.example.shara.data.userpref.UserPreference
import com.example.shara.data.userpref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository{
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.tokenKey)
        return Repository.getInstance(apiService, pref)
    }
}