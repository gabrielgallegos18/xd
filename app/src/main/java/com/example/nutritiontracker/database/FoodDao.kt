package com.example.nutritiontracker.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(foodEntry: FoodEntry): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntries(foodEntries: List<FoodEntry>)
    
    @Update
    suspend fun updateFoodEntry(foodEntry: FoodEntry)
    
    @Delete
    suspend fun deleteFoodEntry(foodEntry: FoodEntry)
    
    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun deleteFoodEntryById(id: Long)
    
    @Query("DELETE FROM food_entries WHERE username = :username")
    suspend fun deleteAllUserFoodEntries(username: String)
    
    @Query("SELECT * FROM food_entries WHERE id = :id")
    suspend fun getFoodEntryById(id: Long): FoodEntry?
    
    @Query("SELECT * FROM food_entries WHERE username = :username ORDER BY date DESC")
    fun getAllFoodEntriesForUser(username: String): Flow<List<FoodEntry>>
    
    @Query("""
        SELECT * FROM food_entries 
        WHERE username = :username 
        AND date >= :startOfDay 
        AND date < :endOfDay 
        ORDER BY date DESC
    """)
    suspend fun getFoodEntriesForDay(username: String, startOfDay: Long, endOfDay: Long): List<FoodEntry>
    
    @Query("""
        SELECT * FROM food_entries 
        WHERE username = :username 
        AND date >= :startOfDay 
        AND date < :endOfDay 
        ORDER BY date DESC
    """)
    fun getFoodEntriesForDayFlow(username: String, startOfDay: Long, endOfDay: Long): Flow<List<FoodEntry>>
    
    @Query("""
        SELECT SUM(totalCalories) FROM food_entries 
        WHERE username = :username 
        AND date >= :startOfDay 
        AND date < :endOfDay
    """)
    suspend fun getTotalCaloriesForDay(username: String, startOfDay: Long, endOfDay: Long): Int?
    
    @Query("""
        SELECT SUM(totalCalories) FROM food_entries 
        WHERE username = :username 
        AND date >= :startOfDay 
        AND date < :endOfDay
    """)
    fun getTotalCaloriesForDayFlow(username: String, startOfDay: Long, endOfDay: Long): Flow<Int?>
    
    @Query("""
        SELECT * FROM food_entries 
        WHERE username = :username 
        AND date >= :startDate 
        AND date < :endDate 
        ORDER BY date DESC
    """)
    suspend fun getFoodEntriesForDateRange(username: String, startDate: Long, endDate: Long): List<FoodEntry>
    
    @Query("""
        SELECT SUM(totalCalories) FROM food_entries 
        WHERE username = :username 
        AND date >= :startDate 
        AND date < :endDate
    """)
    suspend fun getTotalCaloriesForDateRange(username: String, startDate: Long, endDate: Long): Int?
    
    @Query("SELECT DISTINCT foodName FROM food_entries WHERE username = :username ORDER BY foodName ASC")
    suspend fun getUniqueFoodNames(username: String): List<String>
    
    @Query("""
        SELECT AVG(totalCalories) FROM food_entries 
        WHERE username = :username 
        AND date >= :startDate 
        AND date < :endDate
    """)
    suspend fun getAverageCaloriesPerDay(username: String, startDate: Long, endDate: Long): Double?
}