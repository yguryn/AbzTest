package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class PositionDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)