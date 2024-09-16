package com.abz.abztest.di

import com.abz.domain.repository.NetworkStatusTrackerRepository
import com.abz.domain.repository.UserRepository
import com.abz.domain.usecase.userusecase.GetListOfPositionsUseCase
import com.abz.domain.usecase.userusecase.GetTokenUseCase
import com.abz.domain.usecase.userusecase.GetUsersByUrlUseCase
import com.abz.domain.usecase.userusecase.GetUsersUseCase
import com.abz.domain.usecase.networkusecase.NetworkStatusUseCase
import com.abz.domain.usecase.userusecase.RegisterNewUserUseCase
import com.abz.domain.utils.ResourceManager
import com.abz.domain.usecase.validateusecase.ValidateEmailUseCase
import com.abz.domain.usecase.validateusecase.ValidateNameUseCase
import com.abz.domain.usecase.validateusecase.ValidatePhoneUseCase
import com.abz.domain.usecase.validateusecase.ValidatePhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(resourceManager: ResourceManager): ValidateEmailUseCase {
        return ValidateEmailUseCase(resourceManager)
    }

    @Provides
    @Singleton
    fun provideValidatePhoneUseCase(resourceManager: ResourceManager): ValidatePhoneUseCase {
        return ValidatePhoneUseCase(resourceManager)
    }

    @Provides
    @Singleton
    fun provideValidateNameUseCase(resourceManager: ResourceManager): ValidateNameUseCase {
        return ValidateNameUseCase(resourceManager)
    }

    @Provides
    @Singleton
    fun provideValidatePhotoUseCase(resourceManager: ResourceManager): ValidatePhotoUseCase {
        return ValidatePhotoUseCase(resourceManager)
    }

    @Provides
    @Singleton
    fun provideGetListOfPositionsUseCase(userRepository: UserRepository): GetListOfPositionsUseCase {
        return GetListOfPositionsUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetTokenUseCase(userRepository: UserRepository): GetTokenUseCase {
        return GetTokenUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUsersByUseCase(userRepository: UserRepository): GetUsersByUrlUseCase {
        return GetUsersByUrlUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterNewUserUseCase(userRepository: UserRepository): RegisterNewUserUseCase {
        return RegisterNewUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideNetworkStatusTrackerUseCase(networkStatusTrackerRepository: NetworkStatusTrackerRepository): NetworkStatusUseCase {
        return NetworkStatusUseCase(networkStatusTrackerRepository)
    }
}