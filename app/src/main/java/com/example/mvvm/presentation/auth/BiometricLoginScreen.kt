package com.example.mvvm.presentation.auth

import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.mvvm.R
import com.example.mvvm.presentation.components.MultiverseImageBackground

private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

@Composable
fun BiometricLoginScreen(
    onAuthenticationSuccess: () -> Unit
) {

    val context =
        LocalContext.current

    val activity =
        context as? FragmentActivity

    var biometricMessage
            by remember {

                mutableStateOf<String?>(
                    null
                )
            }

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_login_multiverse,

        overlayColor =
            Color.White.copy(
                alpha = 0.05f
            )
    ) {

        BoxWithConstraints(
            modifier =
                Modifier.fillMaxSize()
        ) {

            val horizontalPadding =
                when {

                    maxWidth < 360.dp ->
                        18.dp

                    maxWidth < 600.dp ->
                        26.dp

                    else ->
                        60.dp
                }

            val cardWidthModifier =
                if (
                    maxWidth > 600.dp
                ) {

                    Modifier
                        .fillMaxWidth(
                            0.60f
                        )

                } else {

                    Modifier
                        .fillMaxWidth()
                }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal =
                            horizontalPadding
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Card(
                    modifier =
                        cardWidthModifier,

                    shape =
                        RoundedCornerShape(
                            34.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White.copy(
                                    alpha = 0.93f
                                )
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                8.dp
                        )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 26.dp,
                                vertical = 30.dp
                            ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Surface(
                            modifier =
                                Modifier.size(
                                    92.dp
                                ),

                            shape =
                                CircleShape,

                            color =
                                MultiversePurple
                        ) {

                            Box(
                                modifier =
                                    Modifier.fillMaxSize(),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .Fingerprint,

                                    contentDescription =
                                        null,

                                    modifier =
                                        Modifier.size(
                                            52.dp
                                        ),

                                    tint =
                                        MultiverseGreen
                                )
                            }
                        }

                        Spacer(
                            modifier =
                                Modifier.height(
                                    22.dp
                                )
                        )

                        Text(
                            text =
                                "MULTIVERSO",

                            color =
                                MultiverseGreen,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.ExtraBold,

                            letterSpacing =
                                0.8.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    4.dp
                                )
                        )

                        Text(
                            text =
                                "Bienvenida",

                            color =
                                MultiverseDark,

                            style =
                                MaterialTheme
                                    .typography
                                    .displaySmall,

                            fontWeight =
                                FontWeight.ExtraBold,

                            textAlign =
                                TextAlign.Center
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        Text(
                            text =
                                "Usa tu huella digital para acceder al multiverso.",

                            color =
                                MultiverseGray,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyLarge,

                            textAlign =
                                TextAlign.Center
                        )

                        if (
                            biometricMessage != null
                        ) {

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        18.dp
                                    )
                            )

                            Surface(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        18.dp
                                    ),

                                color =
                                    Color(
                                        0xFFFFEEEE
                                    )
                            ) {

                                Text(
                                    text =
                                        biometricMessage!!,

                                    modifier =
                                        Modifier.padding(
                                            14.dp
                                        ),

                                    color =
                                        Color(
                                            0xFFA92B32
                                        ),

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyMedium,

                                    textAlign =
                                        TextAlign.Center
                                )
                            }
                        }

                        Spacer(
                            modifier =
                                Modifier.height(
                                    26.dp
                                )
                        )

                        Button(
                            onClick = {

                                if (
                                    activity ==
                                    null
                                ) {

                                    biometricMessage =
                                        "No fue posible acceder a la actividad."

                                } else {

                                    authenticateWithFingerprint(
                                        activity =
                                            activity,

                                        onSuccess = {

                                            biometricMessage =
                                                null

                                            onAuthenticationSuccess()
                                        },

                                        onError = {
                                                message ->

                                            biometricMessage =
                                                message
                                        },

                                        onEnrollRequired = {

                                            biometricMessage =
                                                "Debes registrar una huella en el dispositivo."

                                            openBiometricSettings(
                                                activity
                                            )
                                        }
                                    )
                                }
                            },

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(
                                        58.dp
                                    ),

                            shape =
                                RoundedCornerShape(
                                    22.dp
                                )
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default
                                        .Fingerprint,

                                contentDescription =
                                    null,

                                modifier =
                                    Modifier.size(
                                        24.dp
                                    )
                            )

                            Spacer(
                                modifier =
                                    Modifier.size(
                                        10.dp
                                    )
                            )

                            Text(
                                text =
                                    "Ingresar con huella",

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize =
                                    16.sp
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(
                                    18.dp
                                )
                        )

                        Text(
                            text =
                                "Acceso protegido mediante autenticación biométrica",

                            color =
                                MultiverseGray,

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            textAlign =
                                TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun authenticateWithFingerprint(
    activity: FragmentActivity,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    onEnrollRequired: () -> Unit
) {

    val biometricManager =
        BiometricManager.from(
            activity
        )

    val authenticators =
        BiometricManager
            .Authenticators
            .BIOMETRIC_STRONG

    when (
        biometricManager
            .canAuthenticate(
                authenticators
            )
    ) {

        BiometricManager
            .BIOMETRIC_SUCCESS -> {

            val biometricPrompt =
                BiometricPrompt(
                    activity,

                    object :
                        BiometricPrompt
                        .AuthenticationCallback() {

                        override fun onAuthenticationSucceeded(
                            result:
                            BiometricPrompt
                            .AuthenticationResult
                        ) {

                            super
                                .onAuthenticationSucceeded(
                                    result
                                )

                            onSuccess()
                        }

                        override fun onAuthenticationFailed() {

                            super
                                .onAuthenticationFailed()

                            onError(
                                "Huella no reconocida. Inténtalo nuevamente."
                            )
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {

                            super
                                .onAuthenticationError(
                                    errorCode,
                                    errString
                                )

                            when (
                                errorCode
                            ) {

                                BiometricPrompt
                                    .ERROR_NEGATIVE_BUTTON,

                                BiometricPrompt
                                    .ERROR_USER_CANCELED,

                                BiometricPrompt
                                    .ERROR_CANCELED -> {

                                    onError(
                                        "Autenticación cancelada."
                                    )
                                }

                                BiometricPrompt
                                    .ERROR_LOCKOUT -> {

                                    onError(
                                        "Demasiados intentos. Inténtalo nuevamente más tarde."
                                    )
                                }

                                BiometricPrompt
                                    .ERROR_LOCKOUT_PERMANENT -> {

                                    onError(
                                        "La biometría está bloqueada. Desbloquea el dispositivo con tu PIN o contraseña."
                                    )
                                }

                                else -> {

                                    onError(
                                        "Error biométrico: $errString"
                                    )
                                }
                            }
                        }
                    }
                )

            val promptInfo =
                BiometricPrompt
                    .PromptInfo
                    .Builder()
                    .setTitle(
                        "Acceso al Multiverso"
                    )
                    .setSubtitle(
                        "Coloca tu dedo en el sensor"
                    )
                    .setDescription(
                        "Usa tu huella digital para continuar."
                    )
                    .setAllowedAuthenticators(
                        authenticators
                    )
                    .setNegativeButtonText(
                        "Cancelar"
                    )
                    .build()

            biometricPrompt.authenticate(
                promptInfo
            )
        }

        BiometricManager
            .BIOMETRIC_ERROR_NO_HARDWARE -> {

            onError(
                "Este dispositivo no cuenta con lector biométrico."
            )
        }

        BiometricManager
            .BIOMETRIC_ERROR_HW_UNAVAILABLE -> {

            onError(
                "El sensor biométrico no está disponible en este momento."
            )
        }

        BiometricManager
            .BIOMETRIC_ERROR_NONE_ENROLLED -> {

            onEnrollRequired()
        }

        else -> {

            onError(
                "La autenticación biométrica no está disponible."
            )
        }
    }
}

private fun openBiometricSettings(
    activity: FragmentActivity
) {

    try {

        val intent =
            Intent(
                Settings
                    .ACTION_BIOMETRIC_ENROLL
            ).apply {

                putExtra(
                    Settings
                        .EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,

                    BiometricManager
                        .Authenticators
                        .BIOMETRIC_STRONG
                )
            }

        activity.startActivity(
            intent
        )

    } catch (
        e: Exception
    ) {

        activity.startActivity(
            Intent(
                Settings
                    .ACTION_SECURITY_SETTINGS
            )
        )
    }
}