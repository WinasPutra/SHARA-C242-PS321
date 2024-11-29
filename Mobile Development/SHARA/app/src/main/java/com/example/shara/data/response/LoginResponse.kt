package com.example.shara.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("idToken")
	val idToken: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
