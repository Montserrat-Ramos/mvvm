package com.example.mvvm.presentation.auth

data class BiometricLoginUiState(
    val authenticated: Boolean = false,
    val biometricAvailable: Boolean = true,
    val message: String? = null
)