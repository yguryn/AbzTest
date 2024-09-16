package com.abz.domain.model

import com.abz.data.model.LinksDTO
import com.abz.data.model.UserResponseDTO

data class UserResponse(
    val count: Int,
    val linksDTO: LinksDTO,
    val page: Int,
    val success: Boolean,
    val totalPages: Int,
    val totalUsers: Int,
    val users: List<UserResponseDTO>
)