package com.example.shara.ui.auth.register

import androidx.lifecycle.ViewModel
import com.example.shara.data.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {
    fun userReg(name: String, email: String, password: String) = repository.register(name, email, password)
}