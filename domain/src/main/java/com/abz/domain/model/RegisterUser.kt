package com.abz.domain.model

import java.io.File

data class RegisterUser(
    val token: String = "",
    val name: String,
    val email: String,
    val phone: String,
    val positionId: Int,
    val photo: File
)