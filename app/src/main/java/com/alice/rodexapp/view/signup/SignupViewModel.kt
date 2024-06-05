package com.alice.rodexapp.view.signup

import androidx.lifecycle.ViewModel
import com.alice.rodexapp.data.UserRepository

class SignupViewModel(private val repository: UserRepository): ViewModel() {

    fun register(name: String, email: String, password: String) =
        repository.signup(name, email, password)
}