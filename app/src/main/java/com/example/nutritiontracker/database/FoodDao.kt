package com.example.nutritiontracker.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_entries WHERE username = :username AND date >= :startOfDay AND date < :endOfDay ORDER BY date DESC")
    fun getFoodEntriesForDay(username: String, startOfDay: Long, endOfDay: Long): Flow<List<FoodEntry>>
    
    @Query("SELECT SUM(totalCalories) FROM food_entries WHERE username = :username AND date >= :startOfDay AND date < :endOfDay")
    suspend fun getTotalCaloriesForDay(username: String, startOfDay: Long, endOfDay: Long): Int?
    
    @Insert
    suspend fun insertFoodEntry(foodEntry: FoodEntry)
    
    @Delete
    suspend fun deleteFoodEntry(foodEntry: FoodEntry)
    
    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun deleteFoodEntryById(id: Long)
}