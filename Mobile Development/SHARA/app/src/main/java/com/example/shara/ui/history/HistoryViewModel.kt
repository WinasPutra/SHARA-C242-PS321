package com.example.shara.ui.history


import androidx.lifecycle.ViewModel
import com.example.shara.data.Repository

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    fun getHistories() = repository.getHistories()
}