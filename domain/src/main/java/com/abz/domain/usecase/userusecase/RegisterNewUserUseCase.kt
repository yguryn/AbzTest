package com.abz.domain.usecase.userusecase

import com.abz.domain.model.RegisterUser
import com.abz.domain.model.RegisterUserResponse
import com.abz.domain.model.Result
import com.abz.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterNewUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(newUser: RegisterUser): Flow<Result<RegisterUserResponse>> = flow {
        userRepository.getToken().collect { tokenResult ->
            when (tokenResult) {
                is Result.Success -> {
                    if (!tokenResult.data.isNullOrEmpty()) {
                        userRepository.registerNewUser(newUser, tokenResult.data).collect { registerResult ->
                            emit(registerResult)
                        }
                    } else {
                        emit(Result.Error("Token is empty"))
                    }
                }
                is Result.Error -> emit(Result.Error(tokenResult.message ?: "Failed to retrieve token"))
            }
        }
    }
}