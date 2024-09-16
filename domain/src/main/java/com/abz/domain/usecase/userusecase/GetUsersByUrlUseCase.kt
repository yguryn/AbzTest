package com.abz.domain.usecase.userusecase

import com.abz.domain.model.Result
import com.abz.domain.model.UsersResult
import com.abz.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersByUrlUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(url: String): Flow<Result<UsersResult>> {
        return userRepository.getUsersByUrl(url).map { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { userResult ->
                        val sortedUsers =
                            userResult.users.sortedByDescending { it.registrationTimestamp }
                        Result.Success(userResult.copy(users = sortedUsers))
                    } ?: result
                }

                is Result.Error -> result
            }
        }
    }
}