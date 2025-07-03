package com.example.weightlossapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlossapp.data.model.ActivityLevel
import com.example.weightlossapp.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed class to represent Profile UI State more comprehensively if needed later
// For now, just exposing UserProfile directly or null for loading/error.
// sealed class ProfileUiState {
//     object Loading : ProfileUiState()
//     data class Success(val userProfile: UserProfile) : ProfileUiState()
//     data class Error(val message: String) : ProfileUiState()
// }

class ProfileViewModel : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            // Simulate loading data, e.g., from a repository or SharedPreferences
            // For now, we use the placeholder from the UserProfile companion object
            // In a real app, this would involve asynchronous data fetching.
            _userProfile.value = UserProfile.placeholder().copy(
                username = "Jane Doe", // Overriding placeholder for a bit more realism
                email = "jane.doe@example.com",
                age = 28,
                weightKg = 65.2,
                heightCm = 168.0,
                activityLevel = ActivityLevel.LIGHTLY_ACTIVE,
                dailyCalorieGoal = 1800,
                dietaryGoals = "Low carb, high protein"
            )
        }
    }

    fun updateProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            // Here you would add logic to save the updatedProfile
            // to your data source (e.g., SharedPreferences, Room database, backend API)
            // For now, just update the StateFlow
            _userProfile.value = updatedProfile
            // Example: userRepository.saveUserProfile(updatedProfile)
        }
    }

    // Add other functions as needed, e.g., for handling edit mode, saving, etc.
}
