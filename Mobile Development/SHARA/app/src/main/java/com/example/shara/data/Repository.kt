package com.example.shara.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.shara.data.api.ApiService
import com.example.shara.data.model.UserModel
import com.example.shara.data.response.LoginResponse
import com.example.shara.data.response.RegisterResponse
import com.example.shara.data.userpref.UserPreference
import org.json.JSONObject
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun userRegister(
    name: String,
    email: String,
    password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
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
            response.idToken?.let { token ->
                val userModel = UserModel(
                    userId = response.user?.uid ?: "",
                    username = response.user?.email ?: "",
                    email = email,
                    tokenKey = token
                )
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

    fun getSession(): Flow<UserModel>{
        return userPreference.getSession()
    }

    suspend fun logout(){
        userPreference.logout()
    }

    companion object {
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