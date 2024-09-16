package com.abz.domain.utils

import android.content.Context

class ResourceManager(
    private val context: Context
) {
    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}