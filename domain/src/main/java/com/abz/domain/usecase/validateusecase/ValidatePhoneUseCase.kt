package com.abz.domain.usecase.validateusecase

import com.abz.common.R
import com.abz.domain.model.ValidationResult
import com.abz.domain.utils.CheckFormat.isValidPhoneNumber
import com.abz.domain.utils.ResourceManager

class ValidatePhoneUseCase(private val resourceManager: ResourceManager) {
    operator fun invoke(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.required_field)
            )
        }
        if (!isValidPhoneNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.invalid_phone_format)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}