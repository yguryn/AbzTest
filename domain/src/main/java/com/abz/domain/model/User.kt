package com.abz.domain.model

import androidx.compose.ui.graphics.painter.Painter

data class User(
    val name: String,
    val position: String,
    val email: String,
    val phone: String,
    val photo: String,
    val registrationTimestamp: Int
)
