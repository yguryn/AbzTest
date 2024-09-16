package com.abz.data.mapper

import com.abz.data.model.PositionDTO
import com.abz.data.model.RegisterUserResponseDTO
import com.abz.data.model.UsersResponseDTO
import com.abz.domain.model.Fails
import com.abz.domain.model.Position
import com.abz.domain.model.RegisterUserResponse
import com.abz.domain.model.User
import com.abz.domain.model.UsersResult

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
        val fails = if(this.fails == null) {
            null
        } else {
            Fails(
                email = this.fails.email,
                name = this.fails.name,
                phone = this.fails.phone,
                photo = this.fails.photo,
                positionId = this.fails.positionId
            )
        }
        return RegisterUserResponse(
            success = this.success,
            message = this.message,
            fails = fails
        )
    }
}