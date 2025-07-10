package com.example.nutritiontracker.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritiontracker.database.FoodEntry
import com.example.nutritiontracker.database.NutritionDatabase
import com.example.nutritiontracker.database.User
import com.example.nutritiontracker.repository.NutritionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val goalCalories: Int = 2000,
    val consumedCalories: Int = 0,
    val remainingCalories: Int = 2000,
    val foodEntries: List<FoodEntry> = emptyList(),
    val isLoading: Boolean = false
)

class NutritionViewModel(private val context: Context) : ViewModel() {
    
    private val database = NutritionDatabase.getDatabase(context)
    private val repository = NutritionRepository(database.userDao(), database.foodDao())
    private val sharedPreferences = context.getSharedPreferences("nutrition_prefs", Context.MODE_PRIVATE)
    
    // Dashboard state
    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()
    
    // Current user
    private val _currentUser = mutableStateOf<String?>(null)
    val currentUser: State<String?> = _currentUser
    
    // UI states
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage
    
    init {
        _currentUser.value = getLoggedInUser()
        _currentUser.value?.let { username ->
            loadDashboardData(username)
        }
    }
    
    // Authentication functions
    fun loginUser(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = repository.loginUser(username, password)
                if (user != null) {
                    saveLoginState(username)
                    _currentUser.value = username
                    loadDashboardData(username)
                    onResult(true)
                } else {
                    _errorMessage.value = "Invalid username or password"
                    onResult(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun registerUser(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.registerUser(username, password)
                if (success) {
                    _errorMessage.value = null
                    onResult(true)
                } else {
                    _errorMessage.value = "Username already exists"
                    onResult(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Registration failed: ${e.message}"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun logout() {
        clearLoginState()
        _currentUser.value = null
        _dashboardState.value = DashboardState()
    }
    
    // Dashboard functions
    fun loadDashboardData(username: String) {
        viewModelScope.launch {
            try {
                val user = repository.getUser(username)
                val totalCalories = repository.getTotalCaloriesForToday(username)
                val goalCalories = user?.dailyCalorieGoal ?: 2000
                
                repository.getFoodEntriesForToday(username).collect { entries ->
                    _dashboardState.value = _dashboardState.value.copy(
                        goalCalories = goalCalories,
                        consumedCalories = totalCalories,
                        remainingCalories = goalCalories - totalCalories,
                        foodEntries = entries,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data: ${e.message}"
            }
        }
    }
    
    fun updateCalorieGoal(username: String, newGoal: Int) {
        viewModelScope.launch {
            try {
                repository.updateCalorieGoal(username, newGoal)
                loadDashboardData(username)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update goal: ${e.message}"
            }
        }
    }
    
    // Food functions
    fun addFoodEntry(
        username: String,
        foodName: String,
        caloriesPerServing: Int,
        servings: Double,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.addFoodEntry(username, foodName, caloriesPerServing, servings)
                loadDashboardData(username)
                onResult(true)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add food: ${e.message}"
                onResult(false)
            }
        }
    }
    
    fun deleteFoodEntry(entryId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteFoodEntry(entryId)
                _currentUser.value?.let { username ->
                    loadDashboardData(username)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete entry: ${e.message}"
            }
        }
    }
    
    // Helper functions
    fun getLoggedInUser(): String? {
        return sharedPreferences.getString("logged_in_user", null)
    }
    
    private fun saveLoginState(username: String) {
        sharedPreferences.edit()
            .putString("logged_in_user", username)
            .apply()
    }
    
    private fun clearLoginState() {
        sharedPreferences.edit()
            .remove("logged_in_user")
            .apply()
    }
    
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}