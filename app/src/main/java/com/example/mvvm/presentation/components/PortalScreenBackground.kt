package com.example.mvvm.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvvm.R

@Composable
fun PortalScreenBackground(
    modifier: Modifier = Modifier,
    showPortal: Boolean = true,
    showParticles: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Color(0xFFF8FAF7)
            )
    ) {

        val portalSize = when {

            maxWidth < 360.dp -> 170.dp

            maxWidth < 600.dp -> 220.dp

            else -> 280.dp
        }

        val particlesSize = when {

            maxWidth < 360.dp -> 140.dp

            maxWidth < 600.dp -> 190.dp

            else -> 250.dp
        }

        Image(
            painter = painterResource(
                id = R.drawable.portal_nebula
            ),
            contentDescription = null,
            modifier = Modifier
                .size(
                    if (maxWidth < 600.dp)
                        280.dp
                    else
                        420.dp
                )
                .align(
                    Alignment.BottomStart
                )
                .offset(
                    x = (-80).dp,
                    y = 80.dp
                )
                .alpha(0.08f),
            contentScale = ContentScale.Fit
        )

        if (showPortal) {

            Image(
                painter = painterResource(
                    id = R.drawable.portal_vortex
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(portalSize)
                    .align(
                        Alignment.TopEnd
                    )
                    .offset(
                        x = portalSize * 0.25f,
                        y = (-25).dp
                    )
                    .alpha(0.20f),
                contentScale = ContentScale.Fit
            )
        }

        if (showParticles) {

            Image(
                painter = painterResource(
                    id = R.drawable.portal_particles
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(particlesSize)
                    .align(
                        Alignment.TopEnd
                    )
                    .offset(
                        x = 20.dp,
                        y = 70.dp
                    )
                    .alpha(0.22f),
                contentScale = ContentScale.Fit
            )
        }

        content()
    }
}