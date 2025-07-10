package com.example.nutritiontracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "food_entries")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val foodName: String,
    val caloriesPerServing: Int,
    val servings: Double,
    val totalCalories: Int,
    val date: Long = Date().time // Store as timestamp for easy date queries
)