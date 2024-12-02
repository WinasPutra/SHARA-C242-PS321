package com.example.shara.ui.result

import androidx.lifecycle.ViewModel
import com.example.shara.data.Repository

class ResultViewModel(private val repository: Repository) : ViewModel() {
    fun skinResult() = repository.getResult()

    fun getRecommendations() = repository.getRecommendations()
}