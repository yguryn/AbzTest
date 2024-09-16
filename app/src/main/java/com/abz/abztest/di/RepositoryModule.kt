package com.abz.abztest.di

import android.content.Context
import com.abz.data.ApiService
import com.abz.data.repository.NetworkStatusTrackerImpl
import com.abz.data.repository.UserRepositoryImpl
import com.abz.domain.repository.NetworkStatusTrackerRepository
import com.abz.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideUserRepository(
        apiService: ApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    fun provideNetworkStatusTrackerRepository(
        @ApplicationContext context: Context
    ): NetworkStatusTrackerRepository {
        return NetworkStatusTrackerImpl(context)
    }
}