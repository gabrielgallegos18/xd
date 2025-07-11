package com.example.nutritiontracker.data

data class Food(
    val id: Int = 0,
    val name: String,
    val calories: Int,
    val protein: Double = 0.0,
    val carbs: Double = 0.0,
    val fat: Double = 0.0,
    val fiber: Double = 0.0,
    val sugar: Double = 0.0,
    val sodium: Double = 0.0,
    val category: String = "Otros"
)