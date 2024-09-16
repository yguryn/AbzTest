package com.abz.domain.usecase.userusecase

import com.abz.domain.model.Position
import com.abz.domain.model.Result
import com.abz.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetListOfPositionsUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<Result<List<Position>>> {
        return userRepository.getListOfPositions()
    }
}