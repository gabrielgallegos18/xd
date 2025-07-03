package com.example.weightlossapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Sealed class to represent Login UI State
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set // Only ViewModel can set

    var password by mutableStateOf("")
        private set

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun loginUser() {
        if (email.isBlank() || password.isBlank()) {
            _loginUiState.value = LoginUiState.Error("Email and password cannot be empty.")
            return
        }

        _loginUiState.value = LoginUiState.Loading
        // Simulate network call or validation
        // For now, any non-empty email/password is considered a success
        // In a real app, you would add actual authentication logic here (e.g., API call)
        // For this mock, we'll just directly go to success.
        _loginUiState.value = LoginUiState.Success
    }

    fun resetLoginState() {
        _loginUiState.value = LoginUiState.Idle
        // Consider if email/password should also be cleared here or not
    }
}
