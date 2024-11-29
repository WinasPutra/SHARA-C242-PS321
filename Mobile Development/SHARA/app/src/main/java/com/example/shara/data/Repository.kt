package com.example.shara.data

import com.example.shara.data.api.ApiService
import com.example.shara.data.userpref.UserPreference

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {





    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}