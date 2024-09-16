package com.abz.domain.usecase.validateusecase

import com.abz.common.R
import com.abz.domain.model.ValidationResult
import com.abz.domain.utils.CheckFormat.isFileSizeValid
import com.abz.domain.utils.CheckFormat.isImageDimensionsValid
import com.abz.domain.utils.Const.MIN_PHOTO_HEIGHT
import com.abz.domain.utils.Const.MIN_PHOTO_SIZE
import com.abz.domain.utils.Const.MIN_PHOTO_WIDTH
import com.abz.domain.utils.ResourceManager
import java.io.File

class ValidatePhotoUseCase(private val resourceManager: ResourceManager) {
    operator fun invoke(input: File?): ValidationResult {
        if (input == null) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.required_field)
            )
        }
        if(isFileSizeValid(input, MIN_PHOTO_SIZE)) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.photo_may_not_be_grater_than_5_mb)
            )
        }
        if(isImageDimensionsValid(input, MIN_PHOTO_WIDTH, MIN_PHOTO_HEIGHT)) {
            return ValidationResult(
                successful = false,
                errorMessage = resourceManager.getString(R.string.photo_may_not_be_grater_than_5_mb)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}


