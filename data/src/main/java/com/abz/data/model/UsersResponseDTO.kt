package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponseDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("links")
    val linksDTO: LinksDTO,
    @SerializedName("page")
    val page: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_users")
    val totalUsers: Int,
    @SerializedName("users")
    val users: List<UserResponseDTO>
)