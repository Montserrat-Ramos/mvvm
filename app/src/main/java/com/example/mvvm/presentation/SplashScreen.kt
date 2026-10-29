package com.example.mvvm.presentation.splash

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import com.example.mvvm.R

@Composable
fun SplashScreen() {

    val context =
        LocalContext.current

    val imageLoader =
        remember {

            ImageLoader
                .Builder(context)
                .components {

                    if (
                        Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.P
                    ) {
                        add(
                            AnimatedImageDecoder.Factory()
                        )
                    } else {
                        add(
                            GifDecoder.Factory()
                        )
                    }
                }
                .build()
        }

    val request =
        remember {

            ImageRequest
                .Builder(context)
                .data(
                    R.drawable.splash_multiverso
                )
                .build()
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF06333A)
            )
    ) {

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .splash_multiverso_frame
                ),
            contentDescription =
                null,
            modifier =
                Modifier.fillMaxSize(),
            contentScale =
                ContentScale.Crop
        )

        AsyncImage(
            model =
                request,
            imageLoader =
                imageLoader,
            contentDescription =
                null,
            modifier =
                Modifier.fillMaxSize(),
            contentScale =
                ContentScale.Crop
        )
    }
}