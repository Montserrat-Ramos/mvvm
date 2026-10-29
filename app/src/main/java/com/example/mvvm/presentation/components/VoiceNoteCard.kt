package com.example.mvvm.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mvvm.core.audio.AudioPlayerManager
import com.example.mvvm.core.audio.AudioRecorderManager
import java.io.File

@Composable
fun VoiceNoteCard(
    characterId: Int
) {

    val context =
        LocalContext.current

    val recorder =
        remember {
            AudioRecorderManager()
        }

    val player =
        remember {
            AudioPlayerManager()
        }

    val audioDirectory =
        remember {

            File(
                context.filesDir,
                "audio"
            ).apply {

                if (!exists()) {
                    mkdirs()
                }
            }
        }

    val audioFile =
        remember(characterId) {

            File(
                audioDirectory,
                "character_$characterId.m4a"
            )
        }

    var isRecording by remember {
        mutableStateOf(false)
    }

    var isPlaying by remember {
        mutableStateOf(false)
    }

    var hasRecording by remember(
        characterId
    ) {

        mutableStateOf(
            audioFile.exists()
        )
    }

    /*
     * ========================================================
     * PERMISO DEL MICRÓFONO
     * ========================================================
     */

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts
                    .RequestPermission()
        ) { granted ->

            if (granted) {

                val started =
                    recorder.startRecording(
                        context =
                            context,

                        outputFile =
                            audioFile
                    )

                if (started) {

                    isRecording = true

                } else {

                    Toast.makeText(
                        context,
                        "No fue posible iniciar la grabación",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {

                Toast.makeText(
                    context,
                    "Se necesita permiso para usar el micrófono",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    /*
     * Liberamos recursos cuando el
     * componente desaparece.
     */
    DisposableEffect(Unit) {

        onDispose {

            recorder.release()

            player.release()
        }
    }

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                26.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    3.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    18.dp
                )
        ) {

            /*
             * =================================================
             * HEADER
             * =================================================
             */

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier =
                        Modifier.size(
                            44.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            14.dp
                        ),

                    color =
                        MaterialTheme
                            .colorScheme
                            .primaryContainer
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Mic,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.width(
                            12.dp
                        )
                )

                Column {

                    Text(
                        text =
                            "Nota de voz",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            when {

                                isRecording ->
                                    "Grabando..."

                                isPlaying ->
                                    "Reproduciendo..."

                                hasRecording ->
                                    "Nota guardada"

                                else ->
                                    "Sin grabación"
                            },

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        20.dp
                    )
            )

            /*
             * =================================================
             * INDICADOR
             * =================================================
             */

            if (isRecording) {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically,

                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(
                                    12.dp
                                )
                                .background(
                                    Color.Red,
                                    CircleShape
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                8.dp
                            )
                    )

                    Text(
                        text =
                            "Grabando audio",

                        color =
                            MaterialTheme
                                .colorScheme
                                .error,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )
            }

            /*
             * =================================================
             * BOTONES
             * =================================================
             */

            if (!isRecording) {

                Button(
                    onClick = {

                        /*
                         * Detener audio si estaba
                         * reproduciéndose.
                         */
                        if (isPlaying) {

                            player.stop()

                            isPlaying =
                                false
                        }

                        val permission =
                            ContextCompat
                                .checkSelfPermission(
                                    context,

                                    Manifest.permission
                                        .RECORD_AUDIO
                                )

                        if (
                            permission ==
                            PackageManager
                                .PERMISSION_GRANTED
                        ) {

                            val started =
                                recorder
                                    .startRecording(
                                        context =
                                            context,

                                        outputFile =
                                            audioFile
                                    )

                            if (started) {

                                isRecording =
                                    true

                            } else {

                                Toast.makeText(
                                    context,

                                    "No fue posible iniciar la grabación",

                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        } else {

                            permissionLauncher.launch(
                                Manifest.permission
                                    .RECORD_AUDIO
                            )
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Mic,

                        contentDescription =
                            null
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                8.dp
                            )
                    )

                    Text(
                        text =
                            if (
                                hasRecording
                            ) {
                                "Grabar nuevamente"
                            } else {
                                "Grabar nota"
                            }
                    )
                }

            } else {

                Button(
                    onClick = {

                        val success =
                            recorder
                                .stopRecording()

                        isRecording =
                            false

                        hasRecording =
                            success &&
                                    audioFile.exists()

                        if (hasRecording) {

                            Toast.makeText(
                                context,
                                "Nota de voz guardada",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            audioFile.delete()

                            Toast.makeText(
                                context,
                                "La grabación fue demasiado corta o no pudo guardarse",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Stop,

                        contentDescription =
                            null
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                8.dp
                            )
                    )

                    Text(
                        text =
                            "Detener grabación"
                    )
                }
            }

            /*
             * =================================================
             * REPRODUCIR / ELIMINAR
             * =================================================
             */

            if (
                hasRecording &&
                !isRecording
            ) {

                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    OutlinedButton(
                        onClick = {

                            if (isPlaying) {

                                player.stop()

                                isPlaying =
                                    false

                            } else {

                                val started =
                                    player.play(

                                        file =
                                            audioFile,

                                        onCompletion = {

                                            isPlaying =
                                                false
                                        },

                                        onError = {

                                            isPlaying =
                                                false
                                        }
                                    )

                                if (started) {

                                    isPlaying =
                                        true

                                } else {

                                    Toast.makeText(
                                        context,

                                        "No fue posible reproducir la nota",

                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            )
                    ) {

                        Icon(
                            imageVector =
                                if (isPlaying) {

                                    Icons.Default.Stop

                                } else {

                                    Icons.Default.PlayArrow
                                },

                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(
                                    6.dp
                                )
                        )

                        Text(
                            text =
                                if (isPlaying) {

                                    "Detener"

                                } else {

                                    "Reproducir"
                                }
                        )
                    }

                    IconButton(
                        onClick = {

                            player.stop()

                            isPlaying =
                                false

                            if (
                                audioFile.exists()
                            ) {

                                audioFile.delete()
                            }

                            hasRecording =
                                false

                            Toast.makeText(
                                context,
                                "Nota eliminada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Delete,

                            contentDescription =
                                "Eliminar nota",

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }
                }
            }
        }
    }
}