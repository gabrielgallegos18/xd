package com.example.nutritiontracker.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.time.LocalDate

class FoodRepository {
    private var _foodEntries by mutableStateOf<List<FoodEntry>>(emptyList())
    val foodEntries: List<FoodEntry> get() = _foodEntries
    
    private var nextId = 1
    
    // Base de datos simple de alimentos comunes
    private val commonFoods = listOf(
        Food(1, "Manzana", 52, 0.3, 14.0, 0.2, 2.4, 10.0, 1.0, "Frutas"),
        Food(2, "Plátano", 89, 1.1, 23.0, 0.3, 2.6, 12.0, 1.0, "Frutas"),
        Food(3, "Arroz blanco", 130, 2.7, 28.0, 0.3, 0.4, 0.0, 5.0, "Cereales"),
        Food(4, "Pollo a la plancha", 165, 31.0, 0.0, 3.6, 0.0, 0.0, 74.0, "Proteínas"),
        Food(5, "Huevo", 155, 13.0, 1.1, 11.0, 0.0, 0.0, 124.0, "Proteínas"),
        Food(6, "Pan integral", 247, 13.0, 41.0, 4.2, 6.0, 6.0, 491.0, "Cereales"),
        Food(7, "Yogur natural", 59, 10.0, 3.6, 0.4, 0.0, 3.6, 36.0, "Lácteos"),
        Food(8, "Ensalada mixta", 20, 1.4, 4.0, 0.2, 2.0, 2.0, 10.0, "Vegetales"),
        Food(9, "Pasta", 131, 5.0, 25.0, 1.1, 1.8, 0.6, 6.0, "Cereales"),
        Food(10, "Salmón", 208, 20.0, 0.0, 13.0, 0.0, 0.0, 59.0, "Proteínas")
    )
    
    fun getCommonFoods(): List<Food> = commonFoods
    
    fun addFoodEntry(foodEntry: FoodEntry) {
        _foodEntries = _foodEntries + foodEntry.copy(id = nextId++)
    }
    
    fun removeFoodEntry(foodEntry: FoodEntry) {
        _foodEntries = _foodEntries.filterNot { it.id == foodEntry.id }
    }
    
    fun getFoodEntriesForDate(date: LocalDate): List<FoodEntry> {
        return _foodEntries.filter { it.date == date }
    }
    
    fun getTotalCaloriesForDate(date: LocalDate): Int {
        return getFoodEntriesForDate(date).sumOf { it.totalCalories }
    }
    
    fun getFoodById(id: Int): Food? {
        return commonFoods.find { it.id == id }
    }
    
    fun searchFoods(query: String): List<Food> {
        return commonFoods.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.category.contains(query, ignoreCase = true)
        }
    }
}