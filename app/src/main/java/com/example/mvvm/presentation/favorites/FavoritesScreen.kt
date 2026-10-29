package com.example.mvvm.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.mvvm.R
import com.example.mvvm.navigation.AppDestination
import com.example.mvvm.presentation.components.MultiverseCharacterCard
import com.example.mvvm.presentation.components.MultiverseImageBackground

private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel =
        hiltViewModel()
) {

    val state by
    viewModel
        .uiState
        .collectAsStateWithLifecycle()

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_favorites_light,

        overlayColor =
            Color.White.copy(
                alpha = 0.04f
            )
    ) {

        when {

            state.isLoading -> {

                FavoritesLoading()
            }

            state.error != null -> {

                FavoritesError(
                    message =
                        state.error
                            ?: "No fue posible cargar los favoritos"
                )
            }

            state.favorites.isEmpty() -> {

                EmptyFavorites()
            }

            else -> {

                LazyColumn(
                    modifier =
                        Modifier.fillMaxSize(),

                    contentPadding =
                        PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 24.dp,
                            bottom = 30.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            14.dp
                        )
                ) {

                    item {

                        FavoritesHeader(
                            count =
                                state.favorites.size
                        )
                    }

                    item {

                        FavoritesInformationCard(
                            count =
                                state.favorites.size
                        )
                    }

                    items(
                        items =
                            state.favorites,

                        key = {
                                character ->

                            character.id
                        }
                    ) {
                            character ->

                        MultiverseCharacterCard(
                            character =
                                character,

                            onClick = {

                                navController
                                    .navigate(
                                        AppDestination
                                            .Detail
                                            .createRoute(
                                                character.id
                                            )
                                    )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoritesHeader(
    count: Int
) {

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
                "Favoritos",

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
                if (count == 1) {
                    "1 personaje guardado"
                } else {
                    "$count personajes guardados"
                },

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
private fun FavoritesInformationCard(
    count: Int
) {

    Surface(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                24.dp
            ),

        color =
            Color.White.copy(
                alpha = 0.88f
            ),

        tonalElevation =
            1.dp
    ) {

        Column(
            modifier =
                Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 15.dp
                )
        ) {

            Text(
                text =
                    "Tu colección",

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
                        3.dp
                    )
            )

            Text(
                text =
                    if (count == 1) {

                        "Aquí encontrarás el personaje que marcaste como favorito."

                    } else {

                        "Aquí encontrarás los personajes que marcaste como favoritos."
                    },

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
private fun EmptyFavorites() {

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    24.dp
                ),

        contentAlignment =
            Alignment.Center
    ) {

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
                            alpha = 0.94f
                        )
                ),

            elevation =
                CardDefaults.cardElevation(
                    defaultElevation =
                        4.dp
                )
        ) {

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 26.dp,
                            vertical = 34.dp
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Surface(
                    modifier =
                        Modifier.size(
                            74.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            24.dp
                        ),

                    color =
                        Color(
                            0xFFF2E8FF
                        )
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                "♡",

                            color =
                                Color(
                                    0xFF643595
                                ),

                            fontSize =
                                42.sp,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )

                Text(
                    text =
                        "Aún no tienes favoritos",

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
                            8.dp
                        )
                )

                Text(
                    text =
                        "Marca personajes con el corazón para encontrarlos rápidamente aquí.",

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
private fun FavoritesLoading() {

    Box(
        modifier =
            Modifier.fillMaxSize(),

        contentAlignment =
            Alignment.Center
    ) {

        Surface(
            shape =
                RoundedCornerShape(
                    28.dp
                ),

            color =
                Color.White.copy(
                    alpha = 0.92f
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        30.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator(
                    color =
                        MultiverseGreen
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            14.dp
                        )
                )

                Text(
                    text =
                        "Abriendo tu colección...",

                    color =
                        MultiverseDark,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FavoritesError(
    message: String
) {

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    24.dp
                ),

        contentAlignment =
            Alignment.Center
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
                            alpha = 0.95f
                        )
                )
        ) {

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            26.dp
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "No pudimos abrir tus favoritos",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    color =
                        MultiverseDark,

                    fontWeight =
                        FontWeight.Bold,

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
                        message,

                    color =
                        MultiverseGray,

                    textAlign =
                        TextAlign.Center
                )
            }
        }
    }
}