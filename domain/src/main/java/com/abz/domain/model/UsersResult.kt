package com.abz.domain.model

data class UsersResult(
    val users: List<com.abz.domain.model.User>,
    val nextUrl: String?
)