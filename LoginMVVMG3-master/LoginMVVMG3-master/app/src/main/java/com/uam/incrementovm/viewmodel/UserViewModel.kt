package com.uam.incrementovm.viewmodel


import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uam.incrementovm.model.UserState
import com.uam.incrementovm.network.ServiceLocator
import com.uam.incrementovm.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository : UserRepository):ViewModel() {
    private val _users = MutableStateFlow<UserState>(UserState.Idle())
    val users: StateFlow<UserState> = _users


    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.value = UserState.Loading
            val result = userRepository.getUsers()
            result.fold(
                onSuccess = {
                    _users.value = UserState.Success(it)
                },
                onFailure = {
                    _users.value = UserState.Error(it.message?:"No hay registros")
                }
            )
        }
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
