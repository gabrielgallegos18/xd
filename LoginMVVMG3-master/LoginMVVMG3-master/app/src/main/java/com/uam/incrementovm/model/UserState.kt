package com.uam.incrementovm.model



sealed class UserState {
    object Loading : UserState()
    data class Idle(val empty: List<User> = emptyList<User>()) : UserState()
    data class Success(val users: List<User>) : UserState()
    data class Error(val message: String) : UserState()
}