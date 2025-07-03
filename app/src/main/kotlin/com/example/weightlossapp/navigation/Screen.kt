package com.example.weightlossapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object CalorieTracker : Screen("calorie_tracker")
    object ExerciseLog : Screen("exercise_log")
    object WeightTracker : Screen("weight_tracker")
    object Recipe : Screen("recipe")
    object WaterTracker : Screen("water_tracker")
}
