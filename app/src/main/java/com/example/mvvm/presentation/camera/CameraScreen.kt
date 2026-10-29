package com.example.mvvm.presentation.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mvvm.R
import java.io.File
import java.io.FileInputStream

private val PortalGreen =
    Color(0xFF9BE23A)

private val PortalGreenBright =
    Color(0xFFB8FF45)

private val PortalDark =
    Color(0xFF020806)

private val PortalTextSecondary =
    Color(0xFFB8C2BD)

@Composable
fun CameraScreen(
    navController: NavController
) {

    val context =
        LocalContext.current

    var cameraPermissionGranted
            by remember {

                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }

    var cameraType
            by remember {

                mutableIntStateOf(
                    0
                )
            }

    var capturedFile
            by remember {

                mutableStateOf<File?>(
                    null
                )
            }

    val imageCapture =
        remember {

            ImageCapture
                .Builder()
                .setCaptureMode(
                    ImageCapture
                        .CAPTURE_MODE_MINIMIZE_LATENCY
                )
                .build()
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            cameraPermissionGranted =
                granted
        }

    LaunchedEffect(Unit) {

        if (!cameraPermissionGranted) {

            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    if (capturedFile != null) {

        PhotoPreviewScreen(
            file =
                capturedFile!!,

            onRetake = {

                capturedFile
                    ?.delete()

                capturedFile =
                    null
            },

            onSave = {

                val file =
                    capturedFile
                        ?: return@PhotoPreviewScreen

                val uri =
                    savePhotoToGallery(
                        context =
                            context,

                        file =
                            file
                    )

                if (uri != null) {

                    Toast.makeText(
                        context,
                        "Imagen guardada en la galería",
                        Toast.LENGTH_LONG
                    ).show()

                    file.delete()

                    capturedFile =
                        null

                    navController
                        .popBackStack()

                } else {

                    Toast.makeText(
                        context,
                        "No fue posible guardar la imagen",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            onClose = {

                capturedFile
                    ?.delete()

                capturedFile =
                    null

                navController
                    .popBackStack()
            }
        )

        return
    }

    if (!cameraPermissionGranted) {

        CameraPermissionScreen(
            onRequestPermission = {

                permissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },

            onBack = {

                navController
                    .popBackStack()
            }
        )

        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PortalDark
            )
    ) {

        CameraPreview(
            imageCapture =
                imageCapture,

            cameraType =
                cameraType
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors =
                            listOf(
                                Color.Black.copy(
                                    alpha = 0.55f
                                ),
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(
                                    alpha = 0.72f
                                )
                            )
                    )
                )
        )

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_splash
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    265.dp
                )
                .align(
                    Alignment.TopStart
                )
                .alpha(
                    0.46f
                ),

            contentScale =
                ContentScale.Fit
        )

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_vortex
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    270.dp
                )
                .align(
                    Alignment.BottomEnd
                )
                .alpha(
                    0.40f
                ),

            contentScale =
                ContentScale.Fit
        )

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_particles
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    230.dp
                )
                .align(
                    Alignment.CenterStart
                )
                .alpha(
                    0.15f
                ),

            contentScale =
                ContentScale.Fit
        )

        CameraHeader(
            cameraType =
                cameraType,

            onBack = {

                navController
                    .popBackStack()
            },

            onSwitchCamera = {

                cameraType =
                    if (
                        cameraType == 0
                    ) {
                        1
                    } else {
                        0
                    }
            }
        )

        CaptureInformation(
            modifier =
                Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(
                        bottom = 135.dp
                    )
        )

        CaptureButton(
            modifier =
                Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .navigationBarsPadding()
                    .padding(
                        bottom = 28.dp
                    ),

            onClick = {

                takePhoto(
                    context =
                        context,

                    imageCapture =
                        imageCapture,

                    onPhotoCaptured = {
                            file ->

                        capturedFile =
                            file
                    },

                    onError = {
                            message ->

                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        )
    }
}

@Composable
private fun CameraHeader(
    cameraType: Int,
    onBack: () -> Unit,
    onSwitchCamera: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
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
                    CircleShape,

                color =
                    Color.Black.copy(
                        alpha = 0.55f
                    )
            ) {

                IconButton(
                    onClick =
                        onBack
                ) {

                    Text(
                        text =
                            "‹",

                        color =
                            Color.White,

                        fontSize =
                            36.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.width(
                        14.dp
                    )
            )

            Column {

                Text(
                    text =
                        "MULTIVERSO",

                    color =
                        PortalGreenBright,

                    style =
                        MaterialTheme
                            .typography
                            .labelLarge,

                    fontWeight =
                        FontWeight.ExtraBold,

                    letterSpacing =
                        0.8.sp
                )

                Text(
                    text =
                        "Cámara",

                    color =
                        Color.White,

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

        Surface(
            shape =
                RoundedCornerShape(
                    22.dp
                ),

            color =
                Color.Black.copy(
                    alpha = 0.58f
                )
        ) {

            IconButton(
                onClick =
                    onSwitchCamera,

                modifier =
                    Modifier.size(
                        52.dp
                    )
            ) {

                Icon(
                    painter =
                        painterResource(
                            id =
                                R.drawable
                                    .ic_camera
                        ),

                    contentDescription =
                        "Cambiar cámara",

                    tint =
                        if (
                            cameraType == 0
                        ) {

                            PortalGreenBright

                        } else {

                            Color.White
                        },

                    modifier =
                        Modifier.size(
                            26.dp
                        )
                )
            }
        }
    }
}

@Composable
private fun CaptureInformation(
    modifier: Modifier =
        Modifier
) {

    Surface(
        modifier =
            modifier,

        shape =
            RoundedCornerShape(
                22.dp
            ),

        color =
            Color.Black.copy(
                alpha = 0.55f
            )
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 10.dp
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
                            PortalGreenBright,
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
                    "Encuadra y captura tu fotografía",

                color =
                    Color.White,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                fontWeight =
                    FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CaptureButton(
    modifier: Modifier =
        Modifier,
    onClick: () -> Unit
) {

    Surface(
        modifier =
            modifier
                .size(
                    84.dp
                ),

        shape =
            CircleShape,

        color =
            Color.White.copy(
                alpha = 0.95f
            ),

        shadowElevation =
            10.dp
    ) {

        Button(
            onClick =
                onClick,

            modifier =
                Modifier.fillMaxSize(),

            shape =
                CircleShape,

            contentPadding =
                PaddingValues(
                    0.dp
                )
        ) {

            Surface(
                modifier =
                    Modifier.size(
                        62.dp
                    ),

                shape =
                    CircleShape,

                color =
                    PortalGreenBright
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        painter =
                            painterResource(
                                id =
                                    R.drawable
                                        .ic_camera
                            ),

                        contentDescription =
                            "Tomar fotografía",

                        modifier =
                            Modifier.size(
                                28.dp
                            ),

                        tint =
                            PortalDark
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraPreview(
    imageCapture: ImageCapture,
    cameraType: Int
) {

    val context =
        LocalContext.current

    val lifecycleOwner =
        LocalLifecycleOwner.current

    val previewView =
        remember {

            PreviewView(
                context
            ).apply {

                scaleType =
                    PreviewView
                        .ScaleType
                        .FILL_CENTER
            }
        }

    AndroidView(
        factory = {

            previewView
        },

        modifier =
            Modifier.fillMaxSize()
    )

    DisposableEffect(
        cameraType,
        lifecycleOwner
    ) {

        val cameraProviderFuture =
            ProcessCameraProvider
                .getInstance(
                    context
                )

        var cameraProvider:
                ProcessCameraProvider? =
            null

        val executor =
            ContextCompat
                .getMainExecutor(
                    context
                )

        cameraProviderFuture
            .addListener(
                {

                    try {

                        cameraProvider =
                            cameraProviderFuture
                                .get()

                        val preview =
                            Preview
                                .Builder()
                                .build()
                                .also {

                                    it.setSurfaceProvider(
                                        previewView
                                            .surfaceProvider
                                    )
                                }

                        val cameraSelector =
                            if (
                                cameraType == 0
                            ) {

                                CameraSelector
                                    .DEFAULT_BACK_CAMERA

                            } else {

                                CameraSelector
                                    .DEFAULT_FRONT_CAMERA
                            }

                        cameraProvider
                            ?.unbindAll()

                        cameraProvider
                            ?.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageCapture
                            )

                    } catch (
                        e: Exception
                    ) {

                        e.printStackTrace()
                    }

                },

                executor
            )

        onDispose {

            cameraProvider
                ?.unbindAll()
        }
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onPhotoCaptured: (File) -> Unit,
    onError: (String) -> Unit
) {

    val photoFile =
        File.createTempFile(
            "rickmorty_photo_",
            ".jpg",
            context.cacheDir
        )

    val outputOptions =
        ImageCapture
            .OutputFileOptions
            .Builder(
                photoFile
            )
            .build()

    imageCapture.takePicture(
        outputOptions,

        ContextCompat
            .getMainExecutor(
                context
            ),

        object :
            ImageCapture
            .OnImageSavedCallback {

            override fun onImageSaved(
                outputFileResults:
                ImageCapture
                .OutputFileResults
            ) {

                onPhotoCaptured(
                    photoFile
                )
            }

            override fun onError(
                exception:
                ImageCaptureException
            ) {

                photoFile.delete()

                onError(
                    exception.message
                        ?: "No fue posible tomar la fotografía"
                )
            }
        }
    )
}

@Composable
private fun PhotoPreviewScreen(
    file: File,
    onRetake: () -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PortalDark
            )
    ) {

        AsyncImage(
            model =
                file,

            contentDescription =
                "Fotografía capturada",

            contentScale =
                ContentScale.Fit,

            modifier =
                Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(
                                alpha = 0.52f
                            ),
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(
                                alpha = 0.72f
                            )
                        )
                    )
                )
        )

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_particles
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    210.dp
                )
                .align(
                    Alignment.TopEnd
                )
                .alpha(
                    0.16f
                )
        )

        Surface(
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .statusBarsPadding()
                .padding(
                    18.dp
                )
                .size(
                    48.dp
                ),

            shape =
                CircleShape,

            color =
                Color.Black.copy(
                    alpha = 0.55f
                )
        ) {

            IconButton(
                onClick =
                    onClose
            ) {

                Text(
                    text =
                        "×",

                    color =
                        Color.White,

                    fontSize =
                        28.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.BottomCenter
                )
                .navigationBarsPadding()
                .padding(
                    horizontal = 18.dp,
                    vertical = 18.dp
                ),

            shape =
                RoundedCornerShape(
                    30.dp
                ),

            color =
                Color(
                    0xE9111916
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        22.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Surface(
                    modifier =
                        Modifier.size(
                            50.dp
                        ),

                    shape =
                        RoundedCornerShape(
                            17.dp
                        ),

                    color =
                        PortalGreen.copy(
                            alpha = 0.16f
                        )
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            painter =
                                painterResource(
                                    id =
                                        R.drawable
                                            .ic_camera
                                ),

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(
                                    27.dp
                                ),

                            tint =
                                PortalGreenBright
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )

                Text(
                    text =
                        "Vista previa",

                    color =
                        Color.White,

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
                            5.dp
                        )
                )

                Text(
                    text =
                        "¿Quieres guardar esta fotografía?",

                    color =
                        PortalTextSecondary,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium,

                    textAlign =
                        TextAlign.Center
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {

                    OutlinedButton(
                        onClick =
                            onRetake,

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        shape =
                            RoundedCornerShape(
                                22.dp
                            )
                    ) {

                        Text(
                            text =
                                "Repetir",

                            color =
                                Color.White,

                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick =
                            onSave,

                        modifier =
                            Modifier.weight(
                                1f
                            ),

                        shape =
                            RoundedCornerShape(
                                22.dp
                            )
                    ) {

                        Text(
                            text =
                                "Guardar",

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun savePhotoToGallery(
    context: Context,
    file: File
): Uri? {

    val resolver =
        context.contentResolver

    val fileName =
        "rick_morty_${
            System.currentTimeMillis()
        }.jpg"

    val values =
        ContentValues().apply {

            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                fileName
            )

            put(
                MediaStore.Images.Media.MIME_TYPE,
                "image/jpeg"
            )

            if (
                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.Q
            ) {

                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES +
                            "/RickMortyMultiverso"
                )

                put(
                    MediaStore.Images.Media.IS_PENDING,
                    1
                )
            }
        }

    val uri =
        resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
            ?: return null

    return try {

        resolver
            .openOutputStream(
                uri
            )
            ?.use {
                    outputStream ->

                FileInputStream(
                    file
                ).use {
                        inputStream ->

                    inputStream.copyTo(
                        outputStream
                    )
                }
            }

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.Q
        ) {

            values.clear()

            values.put(
                MediaStore.Images.Media.IS_PENDING,
                0
            )

            resolver.update(
                uri,
                values,
                null,
                null
            )
        }

        uri

    } catch (
        e: Exception
    ) {

        resolver.delete(
            uri,
            null,
            null
        )

        null
    }
}

@Composable
private fun CameraPermissionScreen(
    onRequestPermission: () -> Unit,
    onBack: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PortalDark
            )
    ) {

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_vortex
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    300.dp
                )
                .align(
                    Alignment.TopEnd
                )
                .alpha(
                    0.50f
                )
        )

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable
                            .portal_particles
                ),

            contentDescription =
                null,

            modifier = Modifier
                .size(
                    230.dp
                )
                .align(
                    Alignment.BottomStart
                )
                .alpha(
                    0.17f
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    28.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Surface(
                modifier =
                    Modifier.size(
                        88.dp
                    ),

                shape =
                    RoundedCornerShape(
                        28.dp
                    ),

                color =
                    PortalGreen.copy(
                        alpha = 0.15f
                    )
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        painter =
                            painterResource(
                                id =
                                    R.drawable
                                        .ic_camera
                            ),

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(
                                42.dp
                            ),

                        tint =
                            PortalGreenBright
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        22.dp
                    )
            )

            Text(
                text =
                    "Acceso a la cámara",

                color =
                    Color.White,

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

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
                    "Permite el acceso a la cámara para capturar fotografías desde el multiverso.",

                color =
                    PortalTextSecondary,

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,

                textAlign =
                    TextAlign.Center
            )

            Spacer(
                modifier =
                    Modifier.height(
                        26.dp
                    )
            )

            Button(
                onClick =
                    onRequestPermission,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        22.dp
                    )
            ) {

                Text(
                    text =
                        "Permitir cámara",

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
                    onBack,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        22.dp
                    )
            ) {

                Text(
                    text =
                        "Regresar",

                    color =
                        Color.White
                )
            }
        }
    }
}