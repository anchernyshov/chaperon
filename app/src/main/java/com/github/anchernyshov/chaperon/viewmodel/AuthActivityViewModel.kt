package com.github.anchernyshov.chaperon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import com.github.anchernyshov.chaperon.model.LoginResponseModel
import com.github.anchernyshov.chaperon.repository.AuthRepository

class AuthActivityViewModel: ViewModel() {

    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val isLoginButtonEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val loginResponseLiveData = MutableLiveData<LoginResponseModel>()
    private var isUsernameValid = false
    private var isPasswordValid = false

    fun setUsername(s : String) {
        username.postValue(s)
        isUsernameValid = s.length > 2
        isLoginButtonEnabled.postValue(isUsernameValid && isPasswordValid)
    }

    fun setPassword(s : String) {
        password.postValue(s)
        isPasswordValid = s.length > 2
        isLoginButtonEnabled.postValue(isUsernameValid && isPasswordValid)
    }

    fun onLoginButtonClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = AuthRepository.makeLoginRequest(username.value, password.value)
            withContext(Dispatchers.Main) {
                loginResponseLiveData.postValue(response)
            }
        }
    }
}
