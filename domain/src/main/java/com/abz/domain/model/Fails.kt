package com.abz.domain.model

import com.google.gson.annotations.SerializedName

data class Fails(
    val email: List<String>,
    val name: List<String>,
    val phone: List<String>,
    val photo: List<String>,
    val positionId: List<String>
)