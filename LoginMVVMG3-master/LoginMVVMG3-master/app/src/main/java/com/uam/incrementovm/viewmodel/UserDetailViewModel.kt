package com.uam.incrementovm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uam.incrementovm.model.User
import com.uam.incrementovm.repository.UserRepository
import kotlin.jvm.java

class UserDetailViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun getUserById(id: Int): User = repository.getUser(id)
}


class UserDetailViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}