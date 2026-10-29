package com.example.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.mvvm.navigation.AppNavigation
import com.example.mvvm.presentation.auth.BiometricLoginScreen
import com.example.mvvm.presentation.splash.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun AppRoot(
    initialCharacterId: Int? = null
) {

    var showSplash
            by rememberSaveable {

                mutableStateOf(
                    true
                )
            }

    var authenticated
            by rememberSaveable {

                mutableStateOf(
                    false
                )
            }

    LaunchedEffect(
        Unit
    ) {

        delay(3000)

        showSplash =
            false
    }

    when {

        showSplash -> {

            SplashScreen()
        }

        !authenticated -> {

            BiometricLoginScreen(
                onAuthenticationSuccess = {

                    authenticated =
                        true
                }
            )
        }

        else -> {

            AppNavigation(
                initialCharacterId =
                    initialCharacterId
            )
        }
    }
}