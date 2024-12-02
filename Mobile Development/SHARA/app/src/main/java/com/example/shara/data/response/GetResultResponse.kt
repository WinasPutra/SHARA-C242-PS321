package com.example.shara.data.response

import com.google.gson.annotations.SerializedName

data class GetResultResponse(

	@field:SerializedName("history")
	val history: List<HistoryItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
data class RecommendationsItem(

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("product_image")
	val productImage: String? = null,

	@field:SerializedName("product_id")
	val productId: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null
)

data class HistoryItem(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("diagnosis_date")
	val diagnosisDate: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("skin_type")
	val skinType: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null
)
