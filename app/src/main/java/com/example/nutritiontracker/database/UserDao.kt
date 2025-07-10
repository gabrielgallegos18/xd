package com.example.nutritiontracker.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String): User?
    
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun loginUser(username: String, password: String): User?
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long
    
    @Update
    suspend fun updateUser(user: User)
    
    @Query("UPDATE users SET dailyCalorieGoal = :goal WHERE username = :username")
    suspend fun updateCalorieGoal(username: String, goal: Int)
}