package com.abz.data.mapper

import com.abz.data.model.PositionDTO
import com.abz.data.model.RegisterUserResponseDTO
import com.abz.data.model.UsersResponseDTO
import com.abz.domain.model.Position
import com.abz.domain.model.RegisterUserResponse
import com.abz.domain.model.User
import com.abz.domain.model.UsersResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object Mappers {
    fun UsersResponseDTO.toUserResult(): UsersResult {
        val users = this.users.map { userDto ->
            User(
                name = userDto.name,
                position = userDto.position,
                email = userDto.email,
                phone = userDto.phone,
                photo = userDto.photo,
                registrationTimestamp = userDto.registrationTimestamp
            )
        }
        return UsersResult(
            users = users,
            nextUrl = this.linksDTO.nextUrl
        )
    }

    fun PositionDTO.toPosition(): Position {
        return Position(
            id = this.id,
            name = this.name
        )
    }

    fun RegisterUserResponseDTO.toRegisterUserResponse(): RegisterUserResponse {
        return RegisterUserResponse(
            success = this.success,
            message = this.message,
            fails = this.fails
        )
    }

    fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}