package com.abz.domain.model

data class InputErrors(
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val photoError: String? = null
)