package com.example.shara.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shara.data.di.Injection
import com.example.shara.ui.MainViewModel
import com.example.shara.ui.auth.login.LoginViewModel
import com.example.shara.ui.auth.register.RegisterViewModel
import com.example.shara.ui.camera.CameraViewModel
import com.example.shara.ui.history.HistoryViewModel
import com.example.shara.ui.result.ResultViewModel

class ViewModelFactory(private val repository: Repository):
ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}