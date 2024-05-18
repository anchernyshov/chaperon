package com.github.anchernyshov.chaperon.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
    val isProgressBarVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
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
        isLoginButtonEnabled.postValue(false)
        isProgressBarVisible.postValue(true)
        //TODO: actual API request
        Handler(Looper.getMainLooper()).postDelayed({
            isLoginButtonEnabled.postValue(true)
            isProgressBarVisible.postValue(false)
        },2000)
    }
}
