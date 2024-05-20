package com.github.anchernyshov.chaperon.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import com.github.anchernyshov.chaperon.model.LoginResponseModel

object AuthRepository {

    suspend fun makeLoginRequest(username: String?, password: String?): LoginResponseModel = withContext(IO) {
        //TODO actual API request
        delay(1000L)
        return@withContext LoginResponseModel(username + password + java.util.UUID.randomUUID().toString())
    }
}