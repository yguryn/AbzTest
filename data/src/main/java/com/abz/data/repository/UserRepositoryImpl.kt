package com.abz.data.repository

import com.abz.data.ApiService
import com.abz.data.mapper.Mappers.toPosition
import com.abz.data.mapper.Mappers.toRegisterUserResponse
import com.abz.data.mapper.Mappers.toUserResult
import com.abz.data.util.NetworkExtensions.toMultipartBody
import com.abz.data.util.NetworkExtensions.toRequestBody
import com.abz.data.model.RegisterUserResponseDTO
import com.abz.domain.model.Position
import com.abz.domain.model.RegisterUser
import com.abz.domain.model.RegisterUserResponse
import com.abz.domain.model.UsersResult
import com.abz.domain.model.Result
import com.abz.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(page: Int, count: Int): Flow<Result<UsersResult>> {
        return flow {
            try {
                val response = apiService.getUsers(page, count)
                if (response.isSuccessful) {
                    emit(Result.Success(response.body()?.toUserResult()))
                } else {
                    emit(Result.Error(response.message(), null))
                }
            } catch (e: Exception) {
                emit(Result.Error("${e.message}", null))
            }
        }
    }

    override suspend fun getListOfPositions(): Flow<Result<List<Position>>> {
        return flow {
            try {
                val response = apiService.getListOfPositions()
                if (response.isSuccessful) {
                    val positions = response.body()
                    emit(Result.Success(positions?.positionsDTO?.map { it.toPosition() }))
                } else {
                    emit(Result.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Result.Error("${e.message}"))
            }
        }
    }

    override suspend fun getToken(): Flow<Result<String>> {
        return flow {
            try {
                val response = apiService.getToken()
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    emit(Result.Success(token))
                } else {
                    emit(Result.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Result.Error("${e.message}"))
            }
        }
    }

    override suspend fun getUsersByUrl(url: String): Flow<Result<UsersResult>> {
        return flow {
            try {
                val response = apiService.getUsersByUrl(url)
                if (response.isSuccessful) {
                    emit(Result.Success(response.body()?.toUserResult()))
                } else {
                    emit(Result.Error(response.message(), null))
                }
            } catch (e: Exception) {
                emit(Result.Error("${e.message}", null))
            }
        }
    }

    override suspend fun registerNewUser(
        newUser: RegisterUser,
        token: String
    ): Flow<Result<RegisterUserResponse>> {
        return flow {
            val response = apiService.registerNewUser(
                token = token,
                name = newUser.name.toRequestBody(),
                email = newUser.email.toRequestBody(),
                phone = newUser.phone.toRequestBody(),
                positionId = newUser.positionId.toString().toRequestBody(),
                photo = newUser.photo.toMultipartBody()
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it.toRegisterUserResponse()))
                } ?: emit(Result.Error("No data found", null))
            } else {
                val errorBody = response.errorBody()?.charStream().let { reader ->
                    Gson().fromJson(reader, RegisterUserResponseDTO::class.java)
                }
                emit(
                    Result.Success(
                        errorBody?.toRegisterUserResponse() ?: RegisterUserResponse(
                            false,
                            "Error parsing response",
                            null
                        )
                    )
                )
            }
        }
    }
}