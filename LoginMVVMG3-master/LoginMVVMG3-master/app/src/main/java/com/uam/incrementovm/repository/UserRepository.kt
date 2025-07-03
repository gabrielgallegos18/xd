package com.uam.incrementovm.repository

import com.uam.incrementovm.dao.UserDao
import com.uam.incrementovm.model.Users
import com.uam.incrementovm.model.User
import com.uam.incrementovm.network.UserApi

class UserRepository(
    private val userApi: UserApi,
    private val dao: UserDao
) {
    suspend fun getUsers(): Result<Users> {
        return try {
            val response = userApi.getAll()
            if (response.isSuccessful) {
                response.body()?.let {
                    dao.insertAll(it)
                    Result.success(it)
                }
                    ?:Result.failure(Exception("No hay datos"))
            }
            else {
                Result.failure(Exception("Error : ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getUser(id: Int): User = dao.getUserById(id)
}


