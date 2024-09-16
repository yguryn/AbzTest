package com.abz.domain.usecase.networkusecase

import com.abz.domain.model.NetworkStatus
import com.abz.domain.repository.NetworkStatusTrackerRepository
import kotlinx.coroutines.flow.Flow

class NetworkStatusUseCase(private val networkStatusTrackerRepository: NetworkStatusTrackerRepository) {

    operator fun invoke(): Flow<NetworkStatus> {
        return networkStatusTrackerRepository.observeNetworkStatus()
    }

}