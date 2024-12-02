package com.example.shara.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("idToken")
	val idToken: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
