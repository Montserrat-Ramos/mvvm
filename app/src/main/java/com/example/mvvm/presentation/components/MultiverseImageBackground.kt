package com.example.mvvm.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun MultiverseImageBackground(
    @DrawableRes backgroundRes: Int,
    modifier: Modifier = Modifier,
    overlayColor: Color = Color.Transparent,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = backgroundRes
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (
            overlayColor != Color.Transparent
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        overlayColor
                    )
            )
        }

        content()
    }
}