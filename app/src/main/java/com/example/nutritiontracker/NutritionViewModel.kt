package com.example.nutritiontracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.database.*
import com.example.nutritiontracker.repository.NutritionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NutritionViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = NutritionDatabase.getDatabase(application)
    private val repository = NutritionRepository(
        database.userDao(),
        database.foodDao()
    )
    
    // Estado para la UI
    private val _foodEntries = MutableStateFlow<List<FoodEntry>>(emptyList())
    val foodEntries: StateFlow<List<FoodEntry>> = _foodEntries.asStateFlow()
    
    private val _totalCalories = MutableStateFlow(0)
    val totalCalories: StateFlow<Int> = _totalCalories.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Usuario actual (por simplicidad, usamos un usuario por defecto)
    private val currentUsername = "default_user"
    
    init {
        // Crear usuario por defecto si no existe
        viewModelScope.launch {
            val user = repository.getUser(currentUsername)
            if (user == null) {
                repository.registerUser(currentUsername, "password")
            }
            loadTodayData()
        }
    }
    
    private fun loadTodayData() {
        viewModelScope.launch {
            repository.getFoodEntriesForToday(currentUsername).collect { entries ->
                _foodEntries.value = entries
                _totalCalories.value = entries.sumOf { it.totalCalories }
            }
        }
    }
    
    fun addFood(foodName: String, calories: Int) {
        if (foodName.isBlank() || calories <= 0) return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.addFoodEntry(
                    username = currentUsername,
                    foodName = foodName,
                    caloriesPerServing = calories,
                    servings = 1.0
                )
            } catch (e: Exception) {
                // Manejar error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteFood(foodEntry: FoodEntry) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteFoodEntry(foodEntry.id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearAllFoods() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _foodEntries.value.forEach { entry ->
                    repository.deleteFoodEntry(entry.id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}