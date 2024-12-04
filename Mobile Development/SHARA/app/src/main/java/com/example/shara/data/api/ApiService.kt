package com.example.shara.data.api

import com.example.shara.data.response.GetResultResponse
import com.example.shara.data.response.LoginResponse
import com.example.shara.data.response.NewsResponse
import com.example.shara.data.response.RegisterResponse
import com.example.shara.data.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("upload-image")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UploadImageResponse

    @GET("get-history")
    suspend fun getResult(
        @Header("Authorization") token: String
    ): GetResultResponse

    @GET("get-history")
    suspend fun getRecommendationsItem(
        @Header("Authorization") token: String
    ): GetResultResponse

    @GET("get-history")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): GetResultResponse

    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "us",
        @Query("category") category: String = "health"
    ): NewsResponse
}