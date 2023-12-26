package com.hera.sentenscan.data.api.response

import com.google.gson.annotations.SerializedName
import com.hera.sentenscan.data.api.model.User

data class UserResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("data") val users: List<User>
)
