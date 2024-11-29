package com.example.shara.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shara.data.Repository
import com.example.shara.data.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}