package com.abz.domain.model

data class RegisterUserResponse(
    val success: Boolean,
    val message: String,
    val fails: Fails?,
)