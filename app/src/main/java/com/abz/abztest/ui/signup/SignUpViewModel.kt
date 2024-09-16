package com.abz.abztest.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abz.domain.model.InputErrors
import com.abz.domain.model.Position
import com.abz.domain.model.RegisterUser
import com.abz.domain.model.Result
import com.abz.domain.usecase.userusecase.GetListOfPositionsUseCase
import com.abz.domain.usecase.userusecase.RegisterNewUserUseCase
import com.abz.domain.usecase.validateusecase.ValidateEmailUseCase
import com.abz.domain.usecase.validateusecase.ValidateNameUseCase
import com.abz.domain.usecase.validateusecase.ValidatePhoneUseCase
import com.abz.domain.usecase.validateusecase.ValidatePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getListOfPositionsUseCase: GetListOfPositionsUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validatePhotoUseCase: ValidatePhotoUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    private val _positions = MutableStateFlow<List<Position>>(emptyList())
    val positions = _positions.asStateFlow()

    private val _inputErrors = MutableStateFlow(InputErrors())
    val inputErrors: StateFlow<InputErrors> = _inputErrors.asStateFlow()

    init {
        getPositions()
    }

    fun signUpUser(
        name: String,
        email: String,
        phone: String,
        positionId: Int,
        photoFile: File?,
        isSuccess: (Boolean) -> Unit
    ) {

        val nameResult = validateNameUseCase(name)
        val emailResult = validateEmailUseCase(email)
        val phoneResult = validatePhoneUseCase(phone)
        val photoResult = validatePhotoUseCase(photoFile)

        val newInputErrors = InputErrors(
            nameError = nameResult.errorMessage,
            emailError = emailResult.errorMessage,
            phoneError = phoneResult.errorMessage,
            photoError = photoResult.errorMessage
        )

        _inputErrors.value = newInputErrors

        if (!nameResult.successful || !emailResult.successful || !phoneResult.successful || !photoResult.successful) {
            return
        }

        val registerUser = RegisterUser(
            name = name,
            email = email,
            phone = phone,
            positionId = positionId,
            photo = photoFile!!,
        )

        viewModelScope.launch {
            registerNewUserUseCase(registerUser).collect { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data?.success == true) {
                            isSuccess(true)
                        } else if (result.data?.message == "User with this phone or email already exist") {
                            isSuccess(false)
                        } else {
                            _inputErrors.value = InputErrors(
                                nameError = result.data?.fails?.name?.firstOrNull(),
                                emailError = result.data?.fails?.email?.firstOrNull(),
                                phoneError = result.data?.fails?.phone?.firstOrNull(),
                                photoError = result.data?.fails?.photo?.firstOrNull()
                            )
                        }
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    private fun getPositions() {
        viewModelScope.launch {
            getListOfPositionsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _positions.value = result.data ?: emptyList()
                    }
                    is Result.Error -> {
                        _positions.value = emptyList()
                    }
                }
            }
        }
    }
}