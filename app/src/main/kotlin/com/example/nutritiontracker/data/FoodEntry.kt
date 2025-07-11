package com.example.nutritiontracker.data

import java.time.LocalDate

data class FoodEntry(
    val id: Int = 0,
    val food: Food,
    val quantity: Double = 1.0,
    val date: LocalDate = LocalDate.now(),
    val mealType: MealType = MealType.BREAKFAST
) {
    val totalCalories: Int
        get() = (food.calories * quantity).toInt()
    
    val totalProtein: Double
        get() = food.protein * quantity
    
    val totalCarbs: Double
        get() = food.carbs * quantity
    
    val totalFat: Double
        get() = food.fat * quantity
}

enum class MealType(val displayName: String) {
    BREAKFAST("Desayuno"),
    LUNCH("Almuerzo"),
    DINNER("Cena"),
    SNACK("Snack")
}