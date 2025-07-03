package com.example.weightlossapp.data.model

import java.util.Date
import java.util.UUID

// Nutritional information typically in grams for protein, carbs, fats.
// Calories as Int.
data class FoodItem(
    val id: String = UUID.randomUUID().toString(), // Unique ID for each food entry
    val name: String,
    val calories: Int,
    val proteinGrams: Double? = null, // Optional
    val carbsGrams: Double? = null,   // Optional
    val fatGrams: Double? = null,     // Optional
    val servingSize: String,          // e.g., "100g", "1 cup", "1 medium apple"
    val mealType: MealType? = null,   // Optional: Breakfast, Lunch, Dinner, Snack
    val dateLogged: Date = Date()     // Timestamp for when the food was logged
)

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK
}

// Example usage (not part of the class itself, just for illustration):
// val apple = FoodItem(
//     name = "Apple",
//     calories = 95,
//     proteinGrams = 0.3,
//     carbsGrams = 25.0,
//     fatGrams = 0.2,
//     servingSize = "1 medium",
//     mealType = MealType.SNACK
// )
