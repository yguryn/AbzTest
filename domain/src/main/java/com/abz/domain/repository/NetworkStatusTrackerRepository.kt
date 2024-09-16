package com.abz.domain.repository

import com.abz.domain.model.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface NetworkStatusTrackerRepository {
    fun observeNetworkStatus(): Flow<NetworkStatus>
}