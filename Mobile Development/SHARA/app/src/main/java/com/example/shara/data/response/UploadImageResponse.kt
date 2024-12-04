package com.example.shara.data.response

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("classification")
	val classification: Classification? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Classification(

	@field:SerializedName("skin_type")
	val skinType: String? = null
)


