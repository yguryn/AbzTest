package com.abz.abztest.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun getFileFromUri(context: Context, uri: Uri?): File? {
    if (uri == null) return null
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "upload.jpg")
    file.outputStream().use {
        inputStream.copyTo(it)
    }
    return file
}