package com.alice.rodexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alice.rodexapp.pref.UserRepository


class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailStory(id: String) = userRepository.getDetailStory(id)
}