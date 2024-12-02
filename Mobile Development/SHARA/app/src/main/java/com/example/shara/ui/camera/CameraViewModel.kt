package com.example.shara.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shara.data.Repository
import com.example.shara.data.Result
import com.example.shara.data.response.UploadImageResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CameraViewModel(private val repository: Repository) : ViewModel() {
 fun uploadImg(file:MultipartBody.Part) = repository.uploadImage(file)


}