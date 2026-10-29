package com.example.mvvm.presentation.characters

import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import coil3.compose.AsyncImage

import com.example.mvvm.R
import com.example.mvvm.core.CharacterPdfGenerator
import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.navigation.AppDestination
import com.example.mvvm.presentation.character.CharacterFilter
import com.example.mvvm.presentation.character.CharacterListViewModel
import com.example.mvvm.presentation.components.MultiverseCharacterCard
import com.example.mvvm.presentation.components.MultiverseImageBackground

import kotlinx.coroutines.flow.distinctUntilChanged


private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

private val MultiverseLightGreen =
    Color(0xFFDDF6DE)


@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel =
        hiltViewModel()
) {

    val context =
        LocalContext.current

    val state by
    viewModel
        .uiState
        .collectAsStateWithLifecycle()

    val listState =
        rememberLazyListState()

    val pdfGenerator =
        remember {
            CharacterPdfGenerator()
        }

    var charactersToExport
            by remember {

                mutableStateOf(
                    emptyList<CharacterModel>()
                )
            }

    val pdfLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts
                    .CreateDocument(
                        "application/pdf"
                    )
        ) { uri ->

            if (uri == null) {
                return@rememberLauncherForActivityResult
            }

            try {

                context.contentResolver
                    .openOutputStream(
                        uri
                    )
                    ?.use {
                            outputStream ->

                        pdfGenerator
                            .generate(
                                charactersToExport,
                                outputStream
                            )
                    }

                Toast.makeText(
                    context,
                    "PDF generado correctamente",
                    Toast.LENGTH_LONG
                ).show()

            } catch (
                e: Exception
            ) {

                e.printStackTrace()

                Toast.makeText(
                    context,
                    "No fue posible generar el PDF",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    LaunchedEffect(
        listState,
        state.characters.size,
        state.hasNextPage
    ) {

        snapshotFlow {

            listState
                .layoutInfo
                .visibleItemsInfo
                .lastOrNull()
                ?.index
        }
            .distinctUntilChanged()
            .collect {
                    lastVisibleIndex ->

                val lastIndex =
                    state.characters.lastIndex

                if (
                    lastVisibleIndex != null &&
                    lastIndex >= 0 &&
                    lastVisibleIndex >=
                    lastIndex - 3 &&
                    state.hasNextPage &&
                    !state.isLoadingMore
                ) {

                    viewModel
                        .loadNextPage()
                }
            }
    }

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_characters_light,

        overlayColor =
            Color.White.copy(
                alpha = 0.05f
            )
    ) {

        when {

            state.isLoading &&
                    state.characters.isEmpty() -> {

                LoadingState()
            }

            state.error != null &&
                    state.characters.isEmpty() -> {

                ErrorState(
                    message =
                        state.error
                            ?: "Ocurrió un error",

                    onRetry = {

                        viewModel
                            .retry()
                    }
                )
            }

            else -> {

                LazyColumn(
                    state =
                        listState,

                    modifier =
                        Modifier.fillMaxSize(),

                    contentPadding =
                        PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 24.dp,
                            bottom = 28.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            14.dp
                        )
                ) {
                    item {

                        HeaderSection(
                            characterCount =
                                state.characters.size,

                            onExportPdf = {

                                charactersToExport =
                                    viewModel
                                        .getCharactersForExport()

                                if (
                                    charactersToExport
                                        .isEmpty()
                                ) {

                                    Toast.makeText(
                                        context,
                                        "No hay personajes para exportar",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    pdfLauncher.launch(
                                        "personajes_rick_morty.pdf"
                                    )
                                }
                            }
                        )
                    }

                    item {

                        SearchSection(
                            value =
                                state.searchQuery,

                            onValueChange = {

                                viewModel
                                    .onSearchQueryChange(
                                        it
                                    )
                            },

                            onScanQr = {

                                navController
                                    .navigate(
                                        AppDestination
                                            .QrScanner
                                            .route
                                    )
                            },

                            onOpenCamera = {

                                navController
                                    .navigate(
                                        AppDestination
                                            .Camera
                                            .route
                                    )
                            }
                        )
                    }

                    item {

                        FilterSection(
                            selectedFilter =
                                state.selectedFilter,

                            onFilterSelected = {

                                viewModel
                                    .onFilterSelected(
                                        it
                                    )
                            }
                        )
                    }

                    if (
                        state.error != null &&
                        state.characters
                            .isNotEmpty()
                    ) {

                        item {

                            InlineError(
                                message =
                                    state.error!!
                            )
                        }
                    }

                    items(
                        items =
                            state.characters,

                        key = {
                                character ->

                            character.id
                        }
                    ) {
                            character ->

                        MultiverseCharacterCard(
                            character = character,

                            onClick = {

                                navController.navigate(
                                    AppDestination
                                        .Detail
                                        .createRoute(
                                            character.id
                                        )
                                )
                            }
                        )
                    }

                    if (
                        state.isLoadingMore
                    ) {

                        item {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 16.dp
                                    ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                CircularProgressIndicator(
                                    color =
                                        MultiverseGreen,

                                    strokeWidth =
                                        3.dp,

                                    modifier =
                                        Modifier.size(
                                            30.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderSection(
    characterCount: Int,
    onExportPdf: () -> Unit
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.Bottom
    ) {

        Column(
            modifier =
                Modifier
                    .weight(
                        1f
                    )
                    .padding(
                        end = 10.dp
                    )
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
                    "Personajes",

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
                        2.dp
                    )
            )

            Text(
                text =
                    "$characterCount personajes",

                color =
                    MultiverseGray,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )
        }


        /*
         * IMPORTANTE:
         * sigue arriba a la derecha.
         */
        OutlinedButton(
            onClick =
                onExportPdf,

            shape =
                RoundedCornerShape(
                    26.dp
                ),

            contentPadding =
                PaddingValues(
                    horizontal = 14.dp,
                    vertical = 10.dp
                )
        ) {

            /*
             * Pequeño icono PDF sin agregar
             * material-icons-extended.
             */
            Surface(
                shape =
                    RoundedCornerShape(
                        6.dp
                    ),

                color =
                    MultiverseGreen.copy(
                        alpha = 0.15f
                    )
            ) {

                Text(
                    text =
                        "PDF",

                    modifier =
                        Modifier.padding(
                            horizontal = 5.dp,
                            vertical = 3.dp
                        ),

                    color =
                        MultiverseDark,

                    fontSize =
                        8.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )
            }

            Spacer(
                modifier =
                    Modifier.width(
                        7.dp
                    )
            )

            Text(
                text =
                    "Exportar PDF",

                color =
                    MultiverseDark,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SearchSection(
    value: String,
    onValueChange: (String) -> Unit,
    onScanQr: () -> Unit,
    onOpenCamera: () -> Unit
) {

    OutlinedTextField(
        value =
            value,

        onValueChange =
            onValueChange,

        modifier =
            Modifier
                .fillMaxWidth()
                .height(
                    64.dp
                ),

        placeholder = {

            Text(
                text =
                    "Buscar personaje",

                color =
                    MultiverseGray,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.Search,

                contentDescription =
                    "Buscar",

                tint =
                    MultiverseGray,

                modifier =
                    Modifier.size(
                        28.dp
                    )
            )
        },

        trailingIcon = {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                IconButton(
                    onClick =
                        onScanQr
                ) {

                    Icon(
                        painter =
                            painterResource(
                                id =
                                    R.drawable
                                        .ic_qr_scanner
                            ),

                        contentDescription =
                            "Escanear QR",

                        tint =
                            MultiverseGreen,

                        modifier =
                            Modifier.size(
                                25.dp
                            )
                    )
                }
                Box(
                    modifier =
                        Modifier
                            .width(
                                1.dp
                            )
                            .height(
                                27.dp
                            )
                            .background(
                                Color(
                                    0xFFD8DEE2
                                )
                            )
                )
                IconButton(
                    onClick =
                        onOpenCamera
                ) {

                    Icon(
                        painter =
                            painterResource(
                                id =
                                    R.drawable
                                        .ic_camera
                            ),

                        contentDescription =
                            "Abrir cámara",

                        tint =
                            MultiverseGreen,

                        modifier =
                            Modifier.size(
                                27.dp
                            )
                    )
                }
            }
        },

        singleLine =
            true,

        shape =
            RoundedCornerShape(
                32.dp
            ),

        colors =
            OutlinedTextFieldDefaults
                .colors(

                    focusedContainerColor =
                        Color.White.copy(
                            alpha = 0.96f
                        ),

                    unfocusedContainerColor =
                        Color.White.copy(
                            alpha = 0.94f
                        ),

                    focusedBorderColor =
                        MultiverseGreen,

                    unfocusedBorderColor =
                        Color.White.copy(
                            alpha = 0.75f
                        )
                )
    )
}

@Composable
private fun FilterSection(
    selectedFilter: CharacterFilter,
    onFilterSelected: (CharacterFilter) -> Unit
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.spacedBy(
                10.dp
            )
    ) {

        FilterPill(
            text =
                "Todos",

            indicatorColor =
                Color(
                    0xFF482071
                ),

            selected =
                selectedFilter ==
                        CharacterFilter.ALL,

            onClick = {

                onFilterSelected(
                    CharacterFilter.ALL
                )
            }
        )


        FilterPill(
            text =
                "Vivos",

            indicatorColor =
                Color(
                    0xFF3DA833
                ),

            selected =
                selectedFilter ==
                        CharacterFilter.ALIVE,

            onClick = {

                onFilterSelected(
                    CharacterFilter.ALIVE
                )
            }
        )


        FilterPill(
            text =
                "Muertos",

            indicatorColor =
                Color(
                    0xFF697078
                ),

            selected =
                selectedFilter ==
                        CharacterFilter.DEAD,

            onClick = {

                onFilterSelected(
                    CharacterFilter.DEAD
                )
            }
        )
    }
}


@Composable
private fun FilterPill(
    text: String,
    indicatorColor: Color,
    selected: Boolean,
    onClick: () -> Unit
) {

    val background =
        if (selected) {

            MultiversePurple

        } else {

            Color.White.copy(
                alpha = 0.78f
            )
        }


    Surface(
        modifier =
            Modifier
                .height(
                    48.dp
                )
                .clickable(
                    onClick =
                        onClick
                ),

        shape =
            RoundedCornerShape(
                25.dp
            ),

        color =
            background,

        border =
            if (selected) {

                null

            } else {

                androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color =
                        Color(
                            0xFFB7C0C5
                        )
                )
            }
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 18.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.Center
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            8.dp
                        )
                        .background(
                            indicatorColor,
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
                    text,

                color =
                    if (selected) {
                        Color(
                            0xFF351358
                        )
                    } else {
                        MultiverseGray
                    },

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Medium
                    }
            )
        }
    }
}

@Composable
private fun LoadingState() {

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
                        32.dp
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
                            15.dp
                        )
                )

                Text(
                    text =
                        "Explorando el multiverso...",

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
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
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
            shape =
                RoundedCornerShape(
                    28.dp
                ),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            Color.White.copy(
                                alpha = 0.96f
                            )
                    )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        28.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "No pudimos abrir el portal",

                    color =
                        MultiverseDark,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold
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
                        MultiverseGray
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )

                Button(
                    onClick =
                        onRetry,

                    shape =
                        RoundedCornerShape(
                            22.dp
                        )
                ) {

                    Text(
                        text =
                            "Reintentar"
                    )
                }
            }
        }
    }
}

@Composable
private fun InlineError(
    message: String
) {

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
                message,

            modifier =
                Modifier.padding(
                    14.dp
                ),

            color =
                Color(
                    0xFFA92B32
                )
        )
    }
}