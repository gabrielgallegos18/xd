package com.example.weightlossapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlossapp.data.model.FoodItem
import com.example.weightlossapp.data.model.MealType // Assuming MealType is in the same package
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

data class CalorieTrackerUiState(
    val foodItems: List<FoodItem> = emptyList(),
    val totalCaloriesToday: Int = 0,
    val showAddFoodDialog: Boolean = false
    // Add other relevant state like selected date, error messages etc.
)

class CalorieTrackerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalorieTrackerUiState())
    val uiState: StateFlow<CalorieTrackerUiState> = _uiState.asStateFlow()

    init {
        // Load initial data or today's logged items if any (from a repository later)
        // For now, start with an empty list or mock data.
        // To demonstrate, let's add some mock data.
        // In a real app, this would come from a data source.
        // addMockData()
    }

    private fun addMockData() { // Example function
        val mockItems = listOf(
            FoodItem(name = "Breakfast Burrito", calories = 450, servingSize = "1 large", mealType = MealType.BREAKFAST, dateLogged = Date()),
            FoodItem(name = "Apple", calories = 95, servingSize = "1 medium", mealType = MealType.SNACK, dateLogged = Date())
        )
        _uiState.update { currentState ->
            currentState.copy(foodItems = mockItems)
        }
        calculateTotalCalories()
    }


    fun addFoodItem(name: String, calories: Int, servingSize: String, mealType: MealType? = null) {
        viewModelScope.launch {
            val newItem = FoodItem(
                name = name,
                calories = calories,
                servingSize = servingSize,
                mealType = mealType,
                dateLogged = Date() // Logged at the current time
            )
            _uiState.update { currentState ->
                val updatedList = currentState.foodItems + newItem
                currentState.copy(foodItems = updatedList)
            }
            calculateTotalCalories()
            dismissAddFoodDialog() // Close dialog after adding
        }
    }

    fun removeFoodItem(item: FoodItem) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedList = currentState.foodItems - item
                currentState.copy(foodItems = updatedList)
            }
            calculateTotalCalories()
        }
    }

    private fun calculateTotalCalories() {
        val total = _uiState.value.foodItems
            .filter { isToday(it.dateLogged) } // Assuming you want to filter for today
            .sumOf { it.calories }
        _uiState.update { currentState ->
            currentState.copy(totalCaloriesToday = total)
        }
    }

    // Helper function to check if a date is today (simplified)
    // In a real app, use a more robust date comparison library or Calendar class
    private fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val itemDate = Calendar.getInstance()
        itemDate.time = date
        return today.get(Calendar.YEAR) == itemDate.get(Calendar.YEAR) &&
               today.get(Calendar.DAY_OF_YEAR) == itemDate.get(Calendar.DAY_OF_YEAR)
    }


    fun showAddFoodDialog() {
        _uiState.update { it.copy(showAddFoodDialog = true) }
    }

    fun dismissAddFoodDialog() {
        _uiState.update { it.copy(showAddFoodDialog = false) }
    }
}

// Need to import Calendar for isToday helper
import java.util.Calendar
