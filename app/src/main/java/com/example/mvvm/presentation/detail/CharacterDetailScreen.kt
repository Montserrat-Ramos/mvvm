package com.example.mvvm.presentation.detail

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mvvm.R
import com.example.mvvm.core.image.ImageStorageManager
import com.example.mvvm.core.qr.QrGenerator
import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.presentation.components.MultiverseImageBackground
import com.example.mvvm.presentation.components.VoiceNoteCard
import com.example.mvvm.widget.WidgetCharacterStore

private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

private val MultiverseGreenSoft =
    Color(0xFFDDF6DE)

private val MultiverseRedSoft =
    Color(0xFFF5DCDD)

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel =
        hiltViewModel()
) {

    val state by
    viewModel
        .uiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(
        characterId
    ) {

        viewModel.loadCharacter(
            characterId
        )
    }

    when {

        state.isLoading -> {

            DetailLoading()
        }

        state.error != null -> {

            DetailError(
                message =
                    state.error
                        ?: "No fue posible cargar el personaje",

                onBack = {

                    navController
                        .popBackStack()
                }
            )
        }

        state.character != null -> {

            CharacterDetailContent(
                character =
                    state.character!!,

                isFavorite =
                    state.isFavorite,

                onBack = {

                    navController
                        .popBackStack()
                },

                onFavorite = {

                    viewModel
                        .toggleFavorite()
                }
            )
        }
    }
}

@Composable
private fun CharacterDetailContent(
    character: CharacterModel,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavorite: () -> Unit
) {

    val context =
        LocalContext.current

    var qrBitmap
            by remember(
                character.id
            ) {

                mutableStateOf<Bitmap?>(
                    null
                )
            }

    var showQr
            by remember {

                mutableStateOf(
                    false
                )
            }

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_detail_light,

        overlayColor =
            Color.White.copy(
                alpha = 0.03f
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            CharacterHero(
                character =
                    character,

                isFavorite =
                    isFavorite,

                onBack =
                    onBack,

                onFavorite =
                    onFavorite
            )

            CharacterInformation(
                character =
                    character,

                onGenerateQr = {

                    qrBitmap =
                        QrGenerator.generate(
                            content =
                                "rickmorty://character/${character.id}"
                        )

                    showQr =
                        true
                },

                onUseInWidget = {

                    WidgetCharacterStore
                        .saveCharacter(
                            context =
                                context,

                            character =
                                character
                        )

                    Toast.makeText(
                        context,
                        "${character.name} seleccionado para el widget",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    if (
        showQr &&
        qrBitmap != null
    ) {

        CharacterQrDialog(
            character =
                character,

            bitmap =
                qrBitmap!!,

            onDismiss = {

                showQr =
                    false
            },

            onSave = {

                val saved =
                    ImageStorageManager
                        .saveBitmap(
                            context =
                                context,

                            bitmap =
                                qrBitmap!!,

                            fileName =
                                "qr_character_${character.id}.png"
                        )

                if (
                    saved != null
                ) {

                    Toast.makeText(
                        context,
                        "Código QR guardado",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "No fue posible guardar el código QR",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }
}

@Composable
private fun CharacterHero(
    character: CharacterModel,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavorite: () -> Unit
) {

    BoxWithConstraints(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        val heroHeight =
            when {

                maxWidth < 360.dp ->
                    300.dp

                maxWidth < 600.dp ->
                    380.dp

                else ->
                    470.dp
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    heroHeight
                )
        ) {

            AsyncImage(
                model =
                    character.image,

                contentDescription =
                    character.name,

                modifier =
                    Modifier.fillMaxSize(),

                contentScale =
                    ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(
                            alpha = 0.05f
                        )
                    )
            )

            Surface(
                modifier = Modifier
                    .align(
                        Alignment.TopStart
                    )
                    .padding(
                        start = 18.dp,
                        top = 20.dp
                    )
                    .size(
                        48.dp
                    ),

                shape =
                    CircleShape,

                color =
                    Color.White.copy(
                        alpha = 0.94f
                    ),

                shadowElevation =
                    5.dp
            ) {

                IconButton(
                    onClick =
                        onBack
                ) {

                    Text(
                        text =
                            "‹",

                        color =
                            MultiverseDark,

                        fontSize =
                            36.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(
                        end = 18.dp,
                        top = 20.dp
                    )
                    .size(
                        48.dp
                    ),

                shape =
                    CircleShape,

                color =
                    Color.White.copy(
                        alpha = 0.94f
                    ),

                shadowElevation =
                    5.dp
            ) {

                IconButton(
                    onClick =
                        onFavorite
                ) {

                    Icon(
                        imageVector =
                            if (
                                isFavorite
                            ) {

                                Icons.Filled
                                    .Favorite

                            } else {

                                Icons.Outlined
                                    .FavoriteBorder
                            },

                        contentDescription =
                            "Favorito",

                        tint =
                            if (
                                isFavorite
                            ) {

                                Color(
                                    0xFFE94B64
                                )

                            } else {

                                MultiverseDark
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterInformation(
    character: CharacterModel,
    onGenerateQr: () -> Unit,
    onUseInWidget: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(
                y = (-28).dp
            )
            .clip(
                RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
            )
            .background(
                Color.White.copy(
                    alpha = 0.96f
                )
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 26.dp,
                bottom = 28.dp
            )
    ) {

        Text(
            text =
                "PERSONAJE",

            color =
                MultiverseGreen,

            style =
                MaterialTheme
                    .typography
                    .labelLarge,

            fontWeight =
                FontWeight.ExtraBold,

            letterSpacing =
                0.8.sp
        )

        Spacer(
            modifier =
                Modifier.height(
                    3.dp
                )
        )

        Text(
            text =
                character.name,

            color =
                MultiverseDark,

            style =
                MaterialTheme
                    .typography
                    .displaySmall,

            fontWeight =
                FontWeight.ExtraBold,

            maxLines =
                2,

            overflow =
                TextOverflow.Ellipsis
        )

        Spacer(
            modifier =
                Modifier.height(
                    12.dp
                )
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(
                    9.dp
                )
        ) {

            DetailStatusChip(
                status =
                    character.status
            )

            DetailSpeciesChip(
                species =
                    character.species
            )
        }

        Spacer(
            modifier =
                Modifier.height(
                    22.dp
                )
        )

        OutlinedButton(
            onClick =
                onGenerateQr,

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    22.dp
                ),

            contentPadding =
                PaddingValues(
                    vertical = 14.dp
                )
        ) {

            Icon(
                painter =
                    painterResource(
                        id =
                            R.drawable
                                .ic_qr_scanner
                    ),

                contentDescription =
                    null,

                modifier =
                    Modifier.size(
                        22.dp
                    ),

                tint =
                    MultiverseGreen
            )

            Spacer(
                modifier =
                    Modifier.width(
                        9.dp
                    )
            )

            Text(
                text =
                    "Generar código QR",

                color =
                    MultiverseDark,

                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(
                    10.dp
                )
        )

        OutlinedButton(
            onClick =
                onUseInWidget,

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    22.dp
                ),

            contentPadding =
                PaddingValues(
                    vertical = 14.dp
                )
        ) {

            Text(
                text =
                    "⊞",

                color =
                    MultiverseGreen,

                fontSize =
                    21.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.width(
                        9.dp
                    )
            )

            Text(
                text =
                    "Usar en widget",

                color =
                    MultiverseDark,

                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(
                    18.dp
                )
        )

        CharacterInformationCard(
            character =
                character
        )

        Spacer(
            modifier =
                Modifier.height(
                    14.dp
                )
        )

        VoiceNoteCard(
            characterId =
                character.id
        )

        Spacer(
            modifier =
                Modifier.height(
                    14.dp
                )
        )

        IdentifierCard(
            id =
                character.id
        )
    }
}

@Composable
private fun CharacterInformationCard(
    character: CharacterModel
) {

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
                    Color(
                        0xFFF9FAF8
                    )
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    1.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        18.dp
                    )
        ) {

            Text(
                text =
                    "Información",

                color =
                    MultiverseDark,

                style =
                    MaterialTheme
                        .typography
                        .titleLarge,

                fontWeight =
                    FontWeight.ExtraBold
            )

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            InformationRow(
                label =
                    "Género",

                value =
                    character.gender
            )

            InformationRow(
                label =
                    "Origen",

                value =
                    character.origin
            )

            InformationRow(
                label =
                    "Ubicación",

                value =
                    character.location
            )
        }
    }
}

@Composable
private fun InformationRow(
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
            Alignment.Top
    ) {

        Text(
            text =
                label,

            modifier =
                Modifier.weight(
                    0.85f
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
                    1.4f
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
private fun IdentifierCard(
    id: Int
) {

    Surface(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(
                22.dp
            ),

        color =
            MultiversePurple.copy(
                alpha = 0.85f
            )
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 15.dp
                ),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text =
                        "Identificador",

                    color =
                        Color(
                            0xFF5E4E68
                        ),

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall
                )

                Text(
                    text =
                        "Personaje #$id",

                    color =
                        Color(
                            0xFF35145A
                        ),

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold
                )
            }

            Text(
                text =
                    "#$id",

                color =
                    Color(
                        0xFF35145A
                    ),

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                fontWeight =
                    FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun DetailStatusChip(
    status: String
) {

    val isAlive =
        status.equals(
            "Alive",
            ignoreCase = true
        )

    val isDead =
        status.equals(
            "Dead",
            ignoreCase = true
        )

    val background =
        when {

            isAlive ->
                MultiverseGreenSoft

            isDead ->
                MultiverseRedSoft

            else ->
                Color(
                    0xFFE7ECEE
                )
        }

    val foreground =
        when {

            isAlive ->
                Color(
                    0xFF14752A
                )

            isDead ->
                Color(
                    0xFFB32E37
                )

            else ->
                MultiverseGray
        }

    Surface(
        shape =
            RoundedCornerShape(
                22.dp
            ),

        color =
            background
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            8.dp
                        )
                        .background(
                            foreground,
                            CircleShape
                        )
            )

            Spacer(
                modifier =
                    Modifier.width(
                        7.dp
                    )
            )

            Text(
                text =
                    status,

                color =
                    foreground,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DetailSpeciesChip(
    species: String
) {

    Surface(
        shape =
            RoundedCornerShape(
                22.dp
            ),

        color =
            MultiversePurple
    ) {

        Text(
            text =
                species,

            modifier =
                Modifier.padding(
                    horizontal = 15.dp,
                    vertical = 8.dp
                ),

            color =
                Color(
                    0xFF35145A
                ),

            fontWeight =
                FontWeight.Bold
        )
    }
}

@Composable
private fun CharacterQrDialog(
    character: CharacterModel,
    bitmap: Bitmap,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    AlertDialog(
        onDismissRequest =
            onDismiss,

        shape =
            RoundedCornerShape(
                30.dp
            ),

        containerColor =
            Color.White,

        title = {

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "Código QR",

                    color =
                        MultiverseDark,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    text =
                        character.name,

                    color =
                        MultiverseGray,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )
            }
        },

        text = {

            Column(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Surface(
                    shape =
                        RoundedCornerShape(
                            24.dp
                        ),

                    color =
                        Color.White,

                    shadowElevation =
                        3.dp
                ) {

                    Image(
                        bitmap =
                            bitmap
                                .asImageBitmap(),

                        contentDescription =
                            "Código QR de ${character.name}",

                        modifier =
                            Modifier
                                .padding(
                                    12.dp
                                )
                                .size(
                                    230.dp
                                )
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            14.dp
                        )
                )

                Text(
                    text =
                        "Personaje #${character.id}",

                    color =
                        MultiverseDark,

                    fontWeight =
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            4.dp
                        )
                )

                Text(
                    text =
                        "Escanea este código desde la aplicación para abrir directamente el personaje.",

                    color =
                        MultiverseGray,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    textAlign =
                        TextAlign.Center
                )
            }
        },

        confirmButton = {

            Button(
                onClick =
                    onSave,

                shape =
                    RoundedCornerShape(
                        18.dp
                    )
            ) {

                Text(
                    text =
                        "Guardar imagen",

                    fontWeight =
                        FontWeight.Bold
                )
            }
        },

        dismissButton = {

            OutlinedButton(
                onClick =
                    onDismiss,

                shape =
                    RoundedCornerShape(
                        18.dp
                    )
            ) {

                Text(
                    text =
                        "Cerrar"
                )
            }
        }
    )
}

@Composable
private fun DetailLoading() {

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_detail_light
    ) {

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
                        alpha = 0.94f
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
                            "Abriendo el portal...",

                        color =
                            MultiverseDark,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailError(
    message: String,
    onBack: () -> Unit
) {

    MultiverseImageBackground(
        backgroundRes =
            R.drawable
                .bg_detail_light
    ) {

        Box(
            modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            26.dp
                        ),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text =
                            "No pudimos encontrar el personaje",

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
                            message,

                        color =
                            MultiverseGray,

                        textAlign =
                            TextAlign.Center
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                18.dp
                            )
                    )

                    Button(
                        onClick =
                            onBack,

                        shape =
                            RoundedCornerShape(
                                20.dp
                            )
                    ) {

                        Text(
                            text =
                                "Regresar"
                        )
                    }
                }
            }
        }
    }
}