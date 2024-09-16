package com.abz.abztest.ui.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.domain.model.Result
import com.abz.domain.model.User
import com.abz.domain.usecase.userusecase.GetUsersByUrlUseCase
import com.abz.domain.usecase.userusecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUsersByUrlUseCase: GetUsersByUrlUseCase,
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    var nextUrl: String? = null
    private var isLoading = false

    fun loadUsers() {
        if (isLoading || (nextUrl == null && _users.value.isNotEmpty())) return
        isLoading = true
        viewModelScope.launch {
            val resultFlow = if (nextUrl == null) {
                getUsersUseCase(count = 6, page = 1)
            } else {
                getUsersByUrlUseCase(nextUrl!!)
            }
            resultFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        result.data.let {
                            _users.value += it?.users ?: emptyList()
                            nextUrl = it?.nextUrl
                        }
                    }
                    is Result.Error -> {
                    }
                }
                isLoading = false
            }
        }
    }
}