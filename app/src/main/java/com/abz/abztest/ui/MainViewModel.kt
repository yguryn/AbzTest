package com.abz.abztest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.domain.usecase.networkusecase.NetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStatusUseCase: NetworkStatusUseCase
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    val networkStatus = networkStatusUseCase()

    init {
        viewModelScope.launch {
            delay(3000L)
            _isReady.value = true
        }
    }
}