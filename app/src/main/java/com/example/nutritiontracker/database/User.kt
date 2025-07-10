package com.example.nutritiontracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val dailyCalorieGoal: Int = 2000
)