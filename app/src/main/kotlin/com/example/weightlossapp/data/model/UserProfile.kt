package com.example.weightlossapp.data.model

// Using Double for weight and height for flexibility, Int for age.
// ActivityLevel and Goals could be enums or strings depending on desired complexity.
// For now, using String for simplicity.

enum class ActivityLevel {
    SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTRA_ACTIVE
}

data class UserProfile(
    val userId: String, // Could be generated UUID or from auth provider
    val username: String,
    val email: String,
    val age: Int? = null,          // Optional: User might not provide it initially
    val weightKg: Double? = null,  // Optional: Weight in kilograms
    val heightCm: Double? = null, // Optional: Height in centimeters
    val activityLevel: ActivityLevel? = null, // Optional
    val dailyCalorieGoal: Int? = null, // Optional: Calculated or set by user
    val dietaryGoals: String? = null // Optional: e.g., "High protein, low carb"
) {
    // Companion object for providing default or placeholder instances
    companion object {
        fun placeholder(): UserProfile {
            return UserProfile(
                userId = "placeholder_user_id",
                username = "Guest User",
                email = "guest@example.com"
            )
        }
    }
}
