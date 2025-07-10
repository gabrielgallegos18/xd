package com.example.nutritiontracker.repository

import com.example.nutritiontracker.database.*
import kotlinx.coroutines.flow.Flow
import java.util.*

class NutritionRepository(
    private val userDao: UserDao,
    private val foodDao: FoodDao
) {
    
    // User operations
    suspend fun loginUser(username: String, password: String): User? {
        return userDao.loginUser(username, password)
    }
    
    suspend fun registerUser(username: String, password: String): Boolean {
        val user = User(username, password)
        return userDao.insertUser(user) != -1L
    }
    
    suspend fun getUser(username: String): User? {
        return userDao.getUser(username)
    }
    
    suspend fun updateCalorieGoal(username: String, goal: Int) {
        userDao.updateCalorieGoal(username, goal)
    }
    
    // Food operations
    suspend fun addFoodEntry(
        username: String,
        foodName: String,
        caloriesPerServing: Int,
        servings: Double
    ) {
        val totalCalories = (caloriesPerServing * servings).toInt()
        val foodEntry = FoodEntry(
            username = username,
            foodName = foodName,
            caloriesPerServing = caloriesPerServing,
            servings = servings,
            totalCalories = totalCalories
        )
        foodDao.insertFoodEntry(foodEntry)
    }
    
    fun getFoodEntriesForToday(username: String): Flow<List<FoodEntry>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis
        
        return foodDao.getFoodEntriesForDayFlow(username, startOfDay, endOfDay)
    }
    
    suspend fun getTotalCaloriesForToday(username: String): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis
        
        return foodDao.getTotalCaloriesForDay(username, startOfDay, endOfDay) ?: 0
    }
    
    suspend fun deleteFoodEntry(id: Long) {
        foodDao.deleteFoodEntryById(id)
    }
}