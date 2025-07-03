package com.uam.incrementovm.network

import com.uam.incrementovm.model.LoginRequest
import com.uam.incrementovm.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("usuario/login")
    suspend fun login(@Body request: LoginRequest):LoginResponse
}