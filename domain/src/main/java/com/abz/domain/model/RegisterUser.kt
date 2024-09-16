package com.abz.domain.model

import okhttp3.MultipartBody

data class RegisterUser(
    val token: String = "",
    val name: String,
    val email: String,
    val phone: String,
    val positionId: Int,
    val photo: MultipartBody.Part
)