package com.abz.data

import com.abz.data.model.RegisterUserResponseDTO
import com.abz.data.model.UsersResponseDTO
import com.abz.data.model.ResponsePositionsDTO
import com.abz.data.model.ResponseTokenDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("count") count: Int = 6
    ): Response<UsersResponseDTO>

    @GET
    suspend fun getUsersByUrl(@Url url: String): Response<UsersResponseDTO>

    @GET("positions")
    suspend fun getListOfPositions(): Response<ResponsePositionsDTO>

    @GET("token")
    suspend fun getToken(): Response<ResponseTokenDTO>

    @Multipart
    @POST("users")
    suspend fun registerNewUser(
        @Header("Token") token: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("position_id") positionId: RequestBody,
        @Part photo: MultipartBody.Part
    ): Response<RegisterUserResponseDTO>
}

