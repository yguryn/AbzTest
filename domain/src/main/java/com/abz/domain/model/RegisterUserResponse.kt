package com.abz.domain.model

import com.abz.data.model.FailsDTO

data class RegisterUserResponse(
    val success: Boolean,
    val message: String,
    val fails: FailsDTO,
)