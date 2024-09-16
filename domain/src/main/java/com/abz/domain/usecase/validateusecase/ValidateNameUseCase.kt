package com.abz.domain.usecase.validateusecase

import com.abz.common.R
import com.abz.domain.model.ValidationResult
import com.abz.domain.utils.ResourceManager

class ValidateNameUseCase(private val resourceManager: ResourceManager) {
    operator fun invoke(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.required_field)
            )
        }
        if (input.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.name_less_then_2_chars)
            )
        }
        if (input.length > 60) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.name_more_then_60_chars)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}