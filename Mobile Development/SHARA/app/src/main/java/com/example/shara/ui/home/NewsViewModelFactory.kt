package com.example.shara.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shara.data.NewsRepository
import com.example.shara.data.ViewModelFactory
import com.example.shara.data.di.Injection
import com.example.shara.ui.MainViewModel

class NewsViewModelFactory(private val newsRepository: NewsRepository)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: NewsViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): NewsViewModelFactory{
            if(INSTANCE == null){
                synchronized(NewsViewModelFactory::class.java){
                    INSTANCE = NewsViewModelFactory(Injection.provideNewsRepository(context))
                }
            }
            return INSTANCE as NewsViewModelFactory
        }

    }
}