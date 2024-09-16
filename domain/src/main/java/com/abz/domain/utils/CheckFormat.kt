package com.abz.domain.utils

import android.graphics.BitmapFactory
import com.abz.domain.utils.Const.NUMBER_LENGTH
import com.abz.domain.utils.Const.UA_NUMBER_CODE
import java.io.File
import java.util.regex.Pattern

object CheckFormat {
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun isValidEmail(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun isValidPhoneNumber(str: String): Boolean {
        return str.startsWith(UA_NUMBER_CODE) && str.length == NUMBER_LENGTH
    }

    fun isFileSizeValid(file: File, maxSizeInMb: Int): Boolean {
        val maxBytes = maxSizeInMb * Const.BYTES_IN_MB * Const.BYTES_IN_MB
        return file.length() <= maxBytes
    }

    fun isImageDimensionsValid(file: File, minWidth: Int, minHeight: Int): Boolean {
        val bitmap = BitmapFactory.decodeFile(file.path)
        return bitmap.width >= minWidth && bitmap.height >= minHeight
    }
}
