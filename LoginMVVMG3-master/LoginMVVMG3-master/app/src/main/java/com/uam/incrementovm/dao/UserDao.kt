package com.uam.incrementovm.dao

import androidx.room.Dao
import androidx.room.Query
import com.uam.incrementovm.model.User


@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User
}