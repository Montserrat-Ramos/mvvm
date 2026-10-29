package com.example.mvvm.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PortalBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Color(0xFFF8FAF7)
            )
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF97CE4C).copy(alpha = 0.22f),
                        Color(0xFF97CE4C).copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(
                        x = size.width * 0.88f,
                        y = size.height * 0.06f
                    ),
                    radius = size.width * 0.45f
                ),
                radius = size.width * 0.45f,
                center = Offset(
                    x = size.width * 0.88f,
                    y = size.height * 0.06f
                )
            )

            val portalCenter = Offset(
                x = size.width * 0.93f,
                y = size.height * 0.10f
            )

            drawCircle(
                color = Color(0xFF97CE4C)
                    .copy(alpha = 0.12f),
                radius = size.width * 0.23f,
                center = portalCenter
            )

            drawCircle(
                color = Color(0xFF8BED45)
                    .copy(alpha = 0.16f),
                radius = size.width * 0.18f,
                center = portalCenter
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFDCFFB3)
                            .copy(alpha = 0.25f),
                        Color(0xFF97CE4C)
                            .copy(alpha = 0.12f),
                        Color.Transparent
                    )
                ),
                radius = size.width * 0.15f,
                center = portalCenter
            )

            drawCircle(
                color = Color(0xFF97CE4C)
                    .copy(alpha = 0.20f),
                radius = size.width * 0.20f,
                center = portalCenter,
                style = Stroke(
                    width = 3.dp.toPx()
                )
            )

            drawCircle(
                color = Color(0xFF42B4CA)
                    .copy(alpha = 0.12f),
                radius = size.width * 0.16f,
                center = portalCenter,
                style = Stroke(
                    width = 2.dp.toPx()
                )
            )

            val particles = listOf(
                Offset(0.72f, 0.04f),
                Offset(0.79f, 0.07f),
                Offset(0.68f, 0.12f),
                Offset(0.83f, 0.15f),
                Offset(0.90f, 0.18f),
                Offset(0.74f, 0.20f),
                Offset(0.96f, 0.22f),
                Offset(0.86f, 0.25f)
            )

            particles.forEachIndexed { index, position ->

                drawCircle(
                    color = if (index % 2 == 0) {
                        Color(0xFF97CE4C)
                            .copy(alpha = 0.25f)
                    } else {
                        Color(0xFF42B4CA)
                            .copy(alpha = 0.17f)
                    },
                    radius = if (index % 2 == 0) {
                        6.dp.toPx()
                    } else {
                        4.dp.toPx()
                    },
                    center = Offset(
                        x = size.width * position.x,
                        y = size.height * position.y
                    )
                )
            }

            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF42B4CA)
                            .copy(alpha = 0.06f),
                        Color.Transparent
                    )
                ),
                topLeft = Offset(
                    x = -size.width * 0.20f,
                    y = size.height * 0.65f
                ),
                size = Size(
                    width = size.width * 0.80f,
                    height = size.height * 0.45f
                )
            )
        }

        content()
    }
}