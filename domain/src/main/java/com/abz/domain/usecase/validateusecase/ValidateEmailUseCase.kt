package com.abz.domain.usecase.validateusecase

import com.abz.common.R
import com.abz.domain.model.ValidationResult
import com.abz.domain.utils.CheckFormat.isValidEmail
import com.abz.domain.utils.ResourceManager

class ValidateEmailUseCase(private val resourceManager: ResourceManager) {
    operator fun invoke(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.required_field)
            )
        }
        if (!isValidEmail(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.invalid_email_format)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}