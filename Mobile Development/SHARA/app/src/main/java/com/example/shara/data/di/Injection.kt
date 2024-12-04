package com.example.shara.data.di

import android.content.Context
import com.example.shara.data.NewsRepository
import com.example.shara.data.Repository
import com.example.shara.data.api.ApiConfig
import com.example.shara.data.userpref.UserPreference
import com.example.shara.data.userpref.dataStore


object Injection {
    fun provideRepository(context: Context): Repository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService, pref)
    }

   fun provideNewsRepository(context: Context): NewsRepository{
       val apiService = ApiConfig.getNewsApi()
       return NewsRepository.getInstance(apiService)
   }
}