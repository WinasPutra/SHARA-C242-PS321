package com.example.shara.ui.camera


import androidx.lifecycle.ViewModel
import com.example.shara.data.Repository
import okhttp3.MultipartBody

class CameraViewModel(private val repository: Repository) : ViewModel() {
 fun uploadImg(file:MultipartBody.Part) = repository.uploadImage(file)


}