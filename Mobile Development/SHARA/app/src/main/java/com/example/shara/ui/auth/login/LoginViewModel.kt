package com.example.shara.ui.auth.login

import androidx.lifecycle.ViewModel
import com.example.shara.data.Repository

class LoginViewModel(private val repository: Repository): ViewModel() {
    fun userLogin(email: String, password: String) = repository.userLogin(email, password)
}