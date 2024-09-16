package com.abz.domain.repository

import com.abz.domain.model.Position
import com.abz.domain.model.RegisterUser
import com.abz.domain.model.RegisterUserResponse
import com.abz.domain.model.Result
import com.abz.domain.model.UsersResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(page: Int, count: Int): Flow<Result<UsersResult>>
    suspend fun getListOfPositions(): Flow<Result<List<Position>>>
    suspend fun getToken(): Flow<Result<String>>
    suspend fun getUsersByUrl(url: String): Flow<Result<UsersResult>>
    suspend fun registerNewUser(newUser: RegisterUser, token: String): Flow<Result<RegisterUserResponse>>
}