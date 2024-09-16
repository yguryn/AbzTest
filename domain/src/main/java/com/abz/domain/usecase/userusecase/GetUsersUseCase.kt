package com.abz.domain.usecase.userusecase

import com.abz.domain.model.Result
import com.abz.domain.model.UsersResult
import com.abz.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(page: Int, count: Int): Flow<Result<UsersResult>> {
        return userRepository.getUsers(page, count).map { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { userResult ->
                        val sortedUsers = userResult.users.sortedByDescending { it.registrationTimestamp }
                        Result.Success(userResult.copy(users = sortedUsers))
                    } ?: result
                }
                is Result.Error -> result
            }
        }
    }
}