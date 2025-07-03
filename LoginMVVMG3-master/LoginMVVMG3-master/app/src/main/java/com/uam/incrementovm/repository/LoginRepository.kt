package com.uam.incrementovm.repository

import com.uam.incrementovm.model.LoginRequest
import com.uam.incrementovm.model.LoginResponse
import com.uam.incrementovm.network.LoginApi
import com.uam.incrementovm.network.RetrofitInstance
import com.uam.incrementovm.network.ServiceLocator

class LoginRepository(private val api : LoginApi) {

   suspend fun login(username : String,password : String): Result<LoginResponse>
    {
        return try {
            val response = api.login(LoginRequest(username,password))
            Result.success(response)
        }
        catch (e:Exception){
            Result.failure(e)
        }
    }
}