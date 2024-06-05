package com.alice.rodexapp.view.login

import androidx.lifecycle.ViewModel
import com.alice.rodexapp.data.UserRepository
import com.alice.rodexapp.data.userpref.UserModel

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    suspend fun saveSession(userModel: UserModel) {
        repository.saveSession(userModel)
    }
}