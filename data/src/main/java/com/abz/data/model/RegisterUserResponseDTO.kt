package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class RegisterUserResponseDTO(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("fails")
    val fails: FailsDTO,
)
