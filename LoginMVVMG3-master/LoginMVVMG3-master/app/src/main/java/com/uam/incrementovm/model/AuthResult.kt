package com.uam.incrementovm.model

sealed class AuthResult {
    object Idle : AuthResult()
    object Loading : AuthResult()
    data class Success(val user : User) : AuthResult()
    data class Error(val message:String): AuthResult()
}

