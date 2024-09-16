package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class FailsDTO(
    @SerializedName("email")
    val email: List<String>,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("phone")
    val phone: List<String>,
    @SerializedName("photo")
    val photo: List<String>,
    @SerializedName("position_id")
    val positionId: List<String>
)