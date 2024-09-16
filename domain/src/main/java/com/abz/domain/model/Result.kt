package com.abz.domain.model

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): com.abz.domain.model.Result<T>(data)
    class Error<T>(message: String, data: T? = null): com.abz.domain.model.Result<T>(data, message)
}