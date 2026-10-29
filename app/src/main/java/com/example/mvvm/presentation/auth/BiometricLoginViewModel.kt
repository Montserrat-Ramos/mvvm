package com.example.mvvm.presentation.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BiometricLoginViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            BiometricLoginUiState()
        )

    val uiState:
            StateFlow<BiometricLoginUiState> =
        _uiState.asStateFlow()

    fun authenticationSuccess() {

        _uiState.update {
            it.copy(
                authenticated = true,
                message = null
            )
        }
    }

    fun authenticationError(
        message: String
    ) {

        _uiState.update {
            it.copy(
                message = message
            )
        }
    }

    fun biometricUnavailable() {

        _uiState.update {
            it.copy(
                biometricAvailable = false,
                message =
                    "La autenticación biométrica no está disponible en este dispositivo."
            )
        }
    }
}