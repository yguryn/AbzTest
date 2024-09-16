package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class ResponseTokenDTO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
)