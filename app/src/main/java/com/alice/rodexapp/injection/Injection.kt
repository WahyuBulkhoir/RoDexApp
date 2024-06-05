package com.alice.rodexapp.injection

import android.content.Context
import com.alice.rodexapp.data.UserRepository
import com.alice.rodexapp.data.api.ApiConfig
import com.alice.rodexapp.data.userpref.UserPreferences
import com.alice.rodexapp.data.userpref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first()
        }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }
}