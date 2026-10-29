package com.example.mvvm.presentation.about

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Web

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.mvvm.R
import com.example.mvvm.presentation.components.MultiverseImageBackground
import com.example.mvvm.widget.RickMortyWidgetProvider


private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

private val MultiverseGreenSoft =
    Color(0xFFE3F7CD)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutScreen() {

    val context =
        LocalContext.current

    val versionName =
        remember {

            try {

                val packageInfo =
                    if (
                        Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.TIRAMISU
                    ) {

                        context.packageManager
                            .getPackageInfo(
                                context.packageName,
                                PackageManager
                                    .PackageInfoFlags
                                    .of(0)
                            )

                    } else {

                        @Suppress("DEPRECATION")

                        context.packageManager
                            .getPackageInfo(
                                context.packageName,
                                0
                            )
                    }

                packageInfo.versionName
                    ?: "1.0"

            } catch (
                e: Exception
            ) {

                "1.0"
            }
        }


    MultiverseImageBackground(
        backgroundRes =
            R.drawable.bg_about_light,

        overlayColor =
            Color.White.copy(
                alpha = 0.04f
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp,
                    bottom = 32.dp
                )
        ) {

            AboutHeader()

            Spacer(
                modifier =
                    Modifier.height(
                        18.dp
                    )
            )

            AboutHero()

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )

            AboutInformationCard(
                versionName =
                    versionName
            )

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            TechnologiesSection()

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            DataSourceCard()

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            WidgetSection()

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )


            ProjectCard()
        }
    }
}

@Composable
private fun AboutHeader() {

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {

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
                0.6.sp
        )

        Spacer(
            modifier =
                Modifier.height(
                    3.dp
                )
        )

        Text(
            text =
                "Acerca de",

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .displaySmall,

            fontWeight =
                FontWeight.ExtraBold
        )

        Spacer(
            modifier =
                Modifier.height(
                    3.dp
                )
        )

        Text(
            text =
                "Conoce más sobre esta aplicación",

            color =
                MultiverseGray,

            style =
                MaterialTheme
                    .typography
                    .titleMedium
        )
    }
}

@Composable
private fun AboutHero() {

    BoxWithConstraints(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        val portalSize =
            when {

                maxWidth < 360.dp ->
                    165.dp

                maxWidth < 600.dp ->
                    205.dp

                else ->
                    250.dp
            }

        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    30.dp
                ),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color.White.copy(
                            alpha = 0.92f
                        )
                ),

            elevation =
                CardDefaults.cardElevation(
                    defaultElevation =
                        4.dp
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 22.dp,
                        vertical = 22.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Image(
                    painter =
                        painterResource(
                            id =
                                R.drawable
                                    .portal_vortex
                        ),

                    contentDescription =
                        "Portal",

                    modifier =
                        Modifier
                            .size(
                                portalSize
                            )
                            .alpha(
                                0.96f
                            ),

                    contentScale =
                        ContentScale.Fit
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            4.dp
                        )
                )

                Text(
                    text =
                        "Explora el multiverso",

                    color =
                        MultiverseDark,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.ExtraBold,

                    textAlign =
                        TextAlign.Center
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            6.dp
                        )
                )

                Text(
                    text =
                        "Consulta personajes, descubre sus datos, guarda tus favoritos y utiliza las herramientas disponibles.",

                    color =
                        MultiverseGray,

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    textAlign =
                        TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun AboutInformationCard(
    versionName: String
) {

    MultiverseSectionCard {

        SectionTitle(
            icon = {

                Icon(
                    imageVector =
                        Icons.Outlined.Info,

                    contentDescription =
                        null,

                    tint =
                        MultiverseDark
                )
            },

            title =
                "Aplicación"
        )

        Spacer(
            modifier =
                Modifier.height(
                    14.dp
                )
        )

        AboutRow(
            label =
                "Nombre",

            value =
                "Rick & Morty Multiverso"
        )

        AboutRow(
            label =
                "Versión",

            value =
                versionName
        )

        AboutRow(
            label =
                "Plataforma",

            value =
                "Android"
        )

        AboutRow(
            label =
                "Interfaz",

            value =
                "Jetpack Compose"
        )

        AboutRow(
            label =
                "Arquitectura",

            value =
                "MVVM"
        )
    }
}

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
private fun TechnologiesSection() {

    MultiverseSectionCard {

        Text(
            text =
                "Tecnologías",

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .headlineSmall,

            fontWeight =
                FontWeight.ExtraBold
        )

        Spacer(
            modifier =
                Modifier.height(
                    14.dp
                )
        )

        FlowRow(
            horizontalArrangement =
                Arrangement.spacedBy(
                    8.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    8.dp
                )
        ) {

            TechnologyChip(
                text = "Kotlin"
            )

            TechnologyChip(
                text = "Jetpack Compose"
            )

            TechnologyChip(
                text = "MVVM"
            )

            TechnologyChip(
                text = "Retrofit"
            )

            TechnologyChip(
                text = "Hilt"
            )

            TechnologyChip(
                text = "Room"
            )

            TechnologyChip(
                text = "Coroutines"
            )

            TechnologyChip(
                text = "Coil"
            )

            TechnologyChip(
                text = "CameraX"
            )

            TechnologyChip(
                text = "ML Kit"
            )

            TechnologyChip(
                text = "Biometric"
            )

            TechnologyChip(
                text = "MediaRecorder"
            )

            TechnologyChip(
                text = "AppWidget"
            )

            TechnologyChip(
                text = "PDF"
            )

            TechnologyChip(
                text = "QR"
            )
        }
    }
}

@Composable
private fun TechnologyChip(
    text: String
) {

    Surface(
        shape =
            RoundedCornerShape(
                50.dp
            ),

        color =
            MultiversePurple
    ) {

        Text(
            text =
                text,

            modifier =
                Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                ),

            color =
                Color(
                    0xFF35145A
                ),

            style =
                MaterialTheme
                    .typography
                    .labelMedium,

            fontWeight =
                FontWeight.SemiBold
        )
    }
}

@Composable
private fun DataSourceCard() {

    MultiverseSectionCard {

        SectionTitle(
            icon = {

                Icon(
                    imageVector =
                        Icons.Outlined.Web,

                    contentDescription =
                        null,

                    tint =
                        MultiverseDark
                )
            },

            title =
                "Fuente de datos"
        )

        Spacer(
            modifier =
                Modifier.height(
                    14.dp
                )
        )

        Text(
            text =
                "Rick and Morty API",

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .titleLarge,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier =
                Modifier.height(
                    5.dp
                )
        )

        Text(
            text =
                "La información de personajes, especies, estados, ubicaciones e imágenes se obtiene mediante la API pública de Rick and Morty.",

            color =
                MultiverseGray,

            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )
    }
}

@Composable
private fun WidgetSection() {

    val context =
        LocalContext.current

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                28.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MultiverseGreenSoft.copy(
                        alpha = 0.94f
                    )
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
                    20.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier =
                        Modifier.size(
                            48.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            16.dp
                        ),

                    color =
                        Color.White.copy(
                            alpha = 0.90f
                        )
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                "⊞",

                            color =
                                MultiverseGreen,

                            fontSize =
                                27.sp,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.size(
                            12.dp
                        )
                )

                Column {

                    Text(
                        text =
                            "Widget",

                        color =
                            MultiverseDark,

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        fontWeight =
                            FontWeight.ExtraBold
                    )

                    Text(
                        text =
                            "Pantalla principal",

                        color =
                            MultiverseGray,

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )


            Text(
                text =
                    "Agrega un personaje seleccionado directamente a la pantalla principal de tu teléfono.",

                color =
                    MultiverseDark,

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge
            )


            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )

            Button(
                onClick = {

                    if (
                        Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.O
                    ) {

                        val appWidgetManager =
                            AppWidgetManager
                                .getInstance(
                                    context
                                )

                        if (
                            appWidgetManager
                                .isRequestPinAppWidgetSupported
                        ) {

                            val provider =
                                ComponentName(
                                    context,
                                    RickMortyWidgetProvider::
                                    class.java
                                )

                            appWidgetManager
                                .requestPinAppWidget(
                                    provider,
                                    null,
                                    null
                                )

                        } else {

                            Toast.makeText(
                                context,
                                "Tu launcher no permite agregar el widget automáticamente.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {

                        Toast.makeText(
                            context,
                            "Puedes agregar el widget manualmente desde la pantalla principal.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        22.dp
                    )
            ) {

                Text(
                    text =
                        "⊞  Agregar a pantalla principal",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ProjectCard() {

    MultiverseSectionCard {

        SectionTitle(
            icon = {

                Icon(
                    imageVector =
                        Icons.Outlined.Code,

                    contentDescription =
                        null,

                    tint =
                        MultiverseDark
                )
            },

            title =
                "Arquitectura"
        )

        Spacer(
            modifier =
                Modifier.height(
                    16.dp
                )
        )

        ArchitectureItem(
            title =
                "Presentation",

            description =
                "Pantallas, componentes de Jetpack Compose, ViewModels y estados de interfaz."
        )

        Spacer(
            modifier =
                Modifier.height(
                    15.dp
                )
        )

        ArchitectureItem(
            title =
                "Domain",

            description =
                "Modelos, contratos de repositorios y casos de uso."
        )

        Spacer(
            modifier =
                Modifier.height(
                    15.dp
                )
        )

        ArchitectureItem(
            title =
                "Data",

            description =
                "Retrofit, Room, DTOs, entidades, mappers y repositorios."
        )

        Spacer(
            modifier =
                Modifier.height(
                    18.dp
                )
        )

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Surface(
                modifier =
                    Modifier.size(
                        40.dp
                    ),

                shape =
                    RoundedCornerShape(
                        13.dp
                    ),

                color =
                    MultiverseGreenSoft
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.Storage,

                        contentDescription =
                            null,

                        tint =
                            MultiverseGreen
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.size(
                        12.dp
                    )
            )

            Text(
                text =
                    "Los favoritos se almacenan localmente mediante Room.",

                modifier =
                    Modifier.weight(
                        1f
                    ),

                color =
                    MultiverseGray,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )
        }
    }
}

@Composable
private fun MultiverseSectionCard(
    content: @Composable () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                28.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White.copy(
                        alpha = 0.94f
                    )
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    3.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        20.dp
                    )
        ) {

            content()
        }
    }
}

@Composable
private fun SectionTitle(
    icon: @Composable () -> Unit,
    title: String
) {

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
                MultiverseGreenSoft
        ) {

            Box(
                contentAlignment =
                    Alignment.Center
            ) {

                icon()
            }
        }

        Spacer(
            modifier =
                Modifier.size(
                    12.dp
                )
        )

        Text(
            text =
                title,

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .headlineSmall,

            fontWeight =
                FontWeight.ExtraBold
        )
    }
}

@Composable
private fun AboutRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 9.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                label,

            modifier =
                Modifier.weight(
                    1f
                ),

            color =
                MultiverseGray,

            style =
                MaterialTheme
                    .typography
                    .bodyMedium
        )

        Text(
            text =
                value,

            modifier =
                Modifier.weight(
                    1.45f
                ),

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .bodyMedium,

            fontWeight =
                FontWeight.SemiBold,

            textAlign =
                TextAlign.End
        )
    }
}

@Composable
private fun ArchitectureItem(
    title: String,
    description: String
) {

    Column {

        Text(
            text =
                title,

            color =
                MultiverseGreen,

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            fontWeight =
                FontWeight.ExtraBold
        )

        Spacer(
            modifier =
                Modifier.height(
                    3.dp
                )
        )

        Text(
            text =
                description,

            color =
                MultiverseGray,

            style =
                MaterialTheme
                    .typography
                    .bodyMedium
        )
    }
}