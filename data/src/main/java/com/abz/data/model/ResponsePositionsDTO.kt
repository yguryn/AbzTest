package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class ResponsePositionsDTO(
    @SerializedName("positions")
    val positionsDTO: List<PositionDTO>,
    @SerializedName("success")
    val success: Boolean
)