package com.example.shara.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.shara.BuildConfig
import com.example.shara.data.api.ApiService
import com.example.shara.data.response.ArticlesItem


class NewsRepository( private val apiService: ApiService) {

    fun getHealthNews(): LiveData<Result<List<ArticlesItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            emit(Result.Success(response.articles))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService)
            }.also { instance = it }
    }
}