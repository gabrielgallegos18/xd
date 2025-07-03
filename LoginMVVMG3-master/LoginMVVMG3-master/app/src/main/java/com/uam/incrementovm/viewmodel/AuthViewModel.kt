package com.uam.incrementovm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uam.incrementovm.model.AuthResult
import com.uam.incrementovm.model.User
import com.uam.incrementovm.network.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(){

    private val _loginState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val loginState : StateFlow<AuthResult> = _loginState
    private val repository = ServiceLocator.loginRepository

    fun login(username : String, password : String){
        viewModelScope.launch {
            _loginState.value = AuthResult.Loading
            try {
                val response = repository.login(username,password)
                _loginState.value = response.fold(
                    onSuccess = {
                        val user = User(
                            apellido = it.apellido,
                            email = it.email,
                            id = it.id,
                            nombre = it.nombre
                        )
                        AuthResult.Success(user)
                    },
                    onFailure = {
                        AuthResult.Error(it.message ?: "Error desconocido")
                    }
                )
            }
            catch (e : Exception) {
                _loginState.value = AuthResult.Error("Error ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = AuthResult.Idle
    }

}