package com.example.mvvm.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mvvm.domain.model.CharacterModel

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

private val AliveBackground =
    Color(0xFFDDF6DE)

@Composable
fun MultiverseCharacterCard(
    character: CharacterModel,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),

        shape =
            RoundedCornerShape(
                28.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White.copy(
                        alpha = 0.96f
                    )
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    12.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            AsyncImage(
                model =
                    character.image,

                contentDescription =
                    character.name,

                modifier = Modifier
                    .size(
                        104.dp
                    )
                    .clip(
                        RoundedCornerShape(
                            22.dp
                        )
                    ),

                contentScale =
                    ContentScale.Crop
            )

            Spacer(
                modifier =
                    Modifier.width(
                        16.dp
                    )
            )

            Column(
                modifier =
                    Modifier.weight(
                        1f
                    )
            ) {

                Text(
                    text =
                        character.name,

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    color =
                        MultiverseDark,

                    fontWeight =
                        FontWeight.ExtraBold,

                    maxLines =
                        1,

                    overflow =
                        TextOverflow.Ellipsis
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            2.dp
                        )
                )

                Text(
                    text =
                        character.species,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    color =
                        MultiverseGray,

                    maxLines =
                        1,

                    overflow =
                        TextOverflow.Ellipsis
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            9.dp
                        )
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        )
                ) {

                    StatusChip(
                        status =
                            character.status
                    )

                    SpeciesChip(
                        species =
                            character.species
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            9.dp
                        )
                )

                Text(
                    text =
                        character.location,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    color =
                        MultiverseGray,

                    maxLines =
                        1,

                    overflow =
                        TextOverflow.Ellipsis
                )
            }

            Spacer(
                modifier =
                    Modifier.width(
                        8.dp
                    )
            )

            Text(
                text = "›",

                color =
                    MultiverseGray,

                fontSize =
                    38.sp,

                fontWeight =
                    FontWeight.Light
            )
        }
    }
}

@Composable
private fun StatusChip(
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
                AliveBackground

            isDead ->
                Color(
                    0xFFF4DEDF
                )

            else ->
                Color(
                    0xFFE9ECEF
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
                20.dp
            ),

        color =
            background
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 11.dp,
                    vertical = 6.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(
                            7.dp
                        )
                        .background(
                            foreground,
                            CircleShape
                        )
            )

            Spacer(
                modifier =
                    Modifier.width(
                        6.dp
                    )
            )

            Text(
                text =
                    status,

                color =
                    foreground,

                style =
                    MaterialTheme
                        .typography
                        .labelMedium,

                fontWeight =
                    FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SpeciesChip(
    species: String
) {

    Surface(
        shape =
            RoundedCornerShape(
                20.dp
            ),

        color =
            MultiversePurple
    ) {

        Text(
            text =
                species,

            modifier =
                Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
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
                FontWeight.SemiBold,

            maxLines =
                1,

            overflow =
                TextOverflow.Ellipsis
        )
    }
}