package com.vu.s4660013_assignment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vu.s4660013_assignment2.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = apiRepository.login(username, password)
                result.onSuccess { loginResponse ->
                    _loginState.value = LoginState.Success(loginResponse.keypass)
                }.onFailure { exception ->
                    _loginState.value = LoginState.Error(exception.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Network error")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val keypass: String) : LoginState()
    data class Error(val message: String) : LoginState()
}