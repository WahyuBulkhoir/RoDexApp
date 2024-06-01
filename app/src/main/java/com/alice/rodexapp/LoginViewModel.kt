package com.alice.rodexapp

import androidx.lifecycle.ViewModel

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    suspend fun saveSession(userModel: UserModel) {
        repository.saveSession(userModel)
    }
}