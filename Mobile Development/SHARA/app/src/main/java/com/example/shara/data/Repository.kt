package com.example.shara.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.shara.data.api.ApiService
import com.example.shara.data.model.UserModel
import com.example.shara.data.response.GetResultResponse
import com.example.shara.data.response.LoginResponse
import com.example.shara.data.response.RegisterResponse
import com.example.shara.data.response.UploadImageResponse
import com.example.shara.data.userpref.UserPreference
import org.json.JSONObject
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun register(
    name: String,
    email: String,
    password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            Log.d(TAG, "Registration successful for email: $email")
            Log.d(TAG, "Received user ID: ${response.userId}")
            emit(Result.Success(response))
        } catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                JSONObject(it).getString("message")
            } ?: "An error occurred"
            emit(Result.Error(errorMessage))
        }
    }

    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            Log.d(TAG, "Login successful for email: $email")
            response.idToken?.let { token ->
                val userModel = UserModel(
                    userId = response.user?.uid ?: "",
                    username = response.user?.email ?: "",
                    email = email,
                    tokenKey = token
                )
                Log.d(TAG, "Saving user session - User Token: ${userModel.tokenKey}")
                Log.d(TAG, "Saving user session - User ID: ${userModel.userId}")
                userPreference.saveSession(userModel)
            }
            emit(Result.Success(response))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                JSONObject(it).getString("message")
            } ?: "An error occurred"
            emit(Result.Error(errorMessage))
        }
    }

    fun uploadImage(file: MultipartBody.Part): LiveData<Result<UploadImageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = userPreference.getSession().first()
            val token = user.tokenKey
            if (token.isNotEmpty()){
                val response = apiService.uploadImage(file,"Bearer $token")
                emit(Result.Success(response))
            }
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                JSONObject(it).getString("message")
            } ?: "An error occurred"
            emit(Result.Error(errorMessage))
        }
    }

    fun getResult(): LiveData<Result<GetResultResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = userPreference.getSession().first()
            val token = user.tokenKey
            if (token.isNotEmpty()) {
                val response = apiService.getResult("Bearer $token")
                emit(Result.Success(response))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                JSONObject(it).getString("message")
            } ?: "An error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    fun getRecommendations(): LiveData<Result<GetResultResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = userPreference.getSession().first()
            val token = user.tokenKey
            if (token.isNotEmpty()) {
                val response = apiService.getRecommendationsItem("Bearer $token")
                emit(Result.Success(response))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                JSONObject(it).getString("message")
            } ?: "An error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    fun getSession(): Flow<UserModel>{
        Log.d(TAG, "Retrieving user session")
        return userPreference.getSession()
    }

    suspend fun logout(){
        Log.d(TAG, "Logging out user")
        userPreference.logout()
    }

    companion object {
        private const val TAG = "Repository"
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userPreference)
            }.also { instance = it }
    }
}