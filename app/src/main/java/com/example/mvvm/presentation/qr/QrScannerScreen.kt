package com.example.mvvm.presentation.qr

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.mvvm.R
import com.example.mvvm.navigation.AppDestination
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

private val PortalGreen =
    Color(0xFF9BE23A)

private val PortalGreenBright =
    Color(0xFFB8FF45)

private val PortalDark =
    Color(0xFF020806)

private val PortalTextSecondary =
    Color(0xFFB8C2BD)

@Composable
fun QrScannerScreen(
    navController: NavController
) {

    val context =
        LocalContext.current

    var hasCameraPermission
            by remember {

                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) ==
                            PackageManager.PERMISSION_GRANTED
                )
            }

    var message
            by remember {

                mutableStateOf(
                    "Coloca el código QR dentro del marco"
                )
            }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            hasCameraPermission =
                granted
        }

    LaunchedEffect(Unit) {

        if (!hasCameraPermission) {

            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PortalDark
            )
    ) {

        if (hasCameraPermission) {

            CameraQrPreview(
                onQrDetected = {
                        rawValue ->

                    val characterId =
                        extractCharacterId(
                            rawValue
                        )

                    if (characterId != null) {

                        navController.navigate(
                            AppDestination
                                .Detail
                                .createRoute(
                                    characterId
                                )
                        ) {

                            launchSingleTop =
                                true
                        }

                        true

                    } else {

                        message =
                            "Este código QR no pertenece al multiverso."

                        false
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color.Black.copy(
                                        alpha = 0.58f
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
                                .portal_vortex
                    ),

                contentDescription =
                    null,

                modifier = Modifier
                    .size(
                        270.dp
                    )
                    .align(
                        Alignment.TopStart
                    )
                    .padding(
                        start = 0.dp
                    )
                    .alpha(
                        0.58f
                    ),

                contentScale =
                    ContentScale.Fit
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
                        240.dp
                    )
                    .align(
                        Alignment.BottomEnd
                    )
                    .alpha(
                        0.48f
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
                        220.dp
                    )
                    .align(
                        Alignment.CenterEnd
                    )
                    .alpha(
                        0.18f
                    ),

                contentScale =
                    ContentScale.Fit
            )

            ScannerHeader(
                onBack = {

                    navController
                        .popBackStack()
                }
            )

            ScannerFrame(
                modifier =
                    Modifier.align(
                        Alignment.Center
                    )
            )

            ScannerBottomPanel(
                message =
                    message,

                onCancel = {

                    navController
                        .popBackStack()
                },

                modifier =
                    Modifier.align(
                        Alignment.BottomCenter
                    )
            )

        } else {

            CameraPermissionContent(
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
        }
    }
}

@Composable
private fun ScannerHeader(
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = 18.dp,
                vertical = 14.dp
            ),

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
                    alpha = 0.58f
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
                    "Escanear código QR",

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
}

@Composable
private fun ScannerFrame(
    modifier: Modifier =
        Modifier
) {

    Box(
        modifier =
            modifier
                .size(
                    288.dp
                )
                .border(
                    width =
                        2.dp,

                    color =
                        PortalGreen.copy(
                            alpha = 0.35f
                        ),

                    shape =
                        RoundedCornerShape(
                            34.dp
                        )
                )
    ) {

        ScannerCorner(
            modifier =
                Modifier.align(
                    Alignment.TopStart
                ),

            top = true,
            start = true
        )

        ScannerCorner(
            modifier =
                Modifier.align(
                    Alignment.TopEnd
                ),

            top = true,
            start = false
        )

        ScannerCorner(
            modifier =
                Modifier.align(
                    Alignment.BottomStart
                ),

            top = false,
            start = true
        )

        ScannerCorner(
            modifier =
                Modifier.align(
                    Alignment.BottomEnd
                ),

            top = false,
            start = false
        )

        Box(
            modifier =
                Modifier
                    .align(
                        Alignment.Center
                    )
                    .width(
                        230.dp
                    )
                    .height(
                        2.dp
                    )
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                PortalGreenBright,
                                PortalGreenBright,
                                Color.Transparent
                            )
                        )
                    )
        )
    }
}

@Composable
private fun ScannerCorner(
    modifier: Modifier,
    top: Boolean,
    start: Boolean
) {

    Box(
        modifier =
            modifier.size(
                56.dp
            )
    ) {

        Box(
            modifier =
                Modifier
                    .width(
                        56.dp
                    )
                    .height(
                        5.dp
                    )
                    .align(
                        if (top) {
                            Alignment.TopCenter
                        } else {
                            Alignment.BottomCenter
                        }
                    )
                    .background(
                        PortalGreenBright,
                        RoundedCornerShape(
                            50.dp
                        )
                    )
        )

        Box(
            modifier =
                Modifier
                    .width(
                        5.dp
                    )
                    .height(
                        56.dp
                    )
                    .align(
                        if (start) {
                            Alignment.CenterStart
                        } else {
                            Alignment.CenterEnd
                        }
                    )
                    .background(
                        PortalGreenBright,
                        RoundedCornerShape(
                            50.dp
                        )
                    )
        )
    }
}

@Composable
private fun ScannerBottomPanel(
    message: String,
    onCancel: () -> Unit,
    modifier: Modifier =
        Modifier
) {

    Surface(
        modifier =
            modifier
                .fillMaxWidth()
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
            ),

        tonalElevation =
            4.dp
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 22.dp,
                        vertical = 20.dp
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
                                        .ic_qr_scanner
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
                    "Escanea un personaje",

                color =
                    Color.White,

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
                    message,

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
                        16.dp
                    )
            )

            OutlinedButton(
                onClick =
                    onCancel,

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        22.dp
                    )
            ) {

                Text(
                    text =
                        "Cancelar",

                    color =
                        Color.White,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun CameraPermissionContent(
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
                    310.dp
                )
                .align(
                    Alignment.TopEnd
                )
                .alpha(
                    0.55f
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
                    240.dp
                )
                .align(
                    Alignment.BottomStart
                )
                .alpha(
                    0.18f
                ),

            contentScale =
                ContentScale.Fit
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
                        86.dp
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
                                        .ic_qr_scanner
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
                    "Necesitamos utilizar la cámara para reconocer los códigos QR de los personajes.",

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

@Composable
private fun CameraQrPreview(
    onQrDetected: (String) -> Boolean
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

    val scanner =
        remember {

            val options =
                BarcodeScannerOptions
                    .Builder()
                    .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE
                    )
                    .build()

            BarcodeScanning.getClient(
                options
            )
        }

    val executor =
        remember {

            Executors
                .newSingleThreadExecutor()
        }

    val scanning =
        remember {

            AtomicBoolean(
                false
            )
        }

    val completed =
        remember {

            AtomicBoolean(
                false
            )
        }

    AndroidView(
        factory = {

            previewView
        },

        modifier =
            Modifier.fillMaxSize()
    )

    DisposableEffect(
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

        val mainExecutor =
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

                        val imageAnalysis =
                            ImageAnalysis
                                .Builder()
                                .setBackpressureStrategy(
                                    ImageAnalysis
                                        .STRATEGY_KEEP_ONLY_LATEST
                                )
                                .build()

                        imageAnalysis
                            .setAnalyzer(
                                executor
                            ) {
                                    imageProxy ->

                                analyzeQrImage(
                                    imageProxy =
                                        imageProxy,

                                    scanner =
                                        scanner,

                                    scanning =
                                        scanning,

                                    completed =
                                        completed,

                                    mainExecutor =
                                        mainExecutor,

                                    onQrDetected =
                                        onQrDetected
                                )
                            }

                        cameraProvider
                            ?.unbindAll()

                        cameraProvider
                            ?.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector
                                    .DEFAULT_BACK_CAMERA,
                                preview,
                                imageAnalysis
                            )

                    } catch (
                        e: Exception
                    ) {

                        e.printStackTrace()
                    }

                },

                mainExecutor
            )

        onDispose {

            cameraProvider
                ?.unbindAll()

            scanner.close()

            executor.shutdown()
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(
    ExperimentalGetImage::class
)
private fun analyzeQrImage(
    imageProxy: ImageProxy,
    scanner: BarcodeScanner,
    scanning: AtomicBoolean,
    completed: AtomicBoolean,
    mainExecutor: java.util.concurrent.Executor,
    onQrDetected: (String) -> Boolean
) {

    if (
        completed.get() ||
        !scanning.compareAndSet(
            false,
            true
        )
    ) {

        imageProxy.close()

        return
    }

    val mediaImage =
        imageProxy.image

    if (mediaImage == null) {

        scanning.set(
            false
        )

        imageProxy.close()

        return
    }

    val image =
        InputImage.fromMediaImage(
            mediaImage,
            imageProxy
                .imageInfo
                .rotationDegrees
        )

    scanner.process(
        image
    )
        .addOnSuccessListener(
            mainExecutor
        ) {
                barcodes ->

            val rawValue =
                barcodes
                    .firstOrNull()
                    ?.rawValue

            if (
                !rawValue.isNullOrBlank()
            ) {

                val accepted =
                    onQrDetected(
                        rawValue
                    )

                if (accepted) {

                    completed.set(
                        true
                    )
                }
            }
        }
        .addOnCompleteListener(
            mainExecutor
        ) {

            scanning.set(
                false
            )

            imageProxy.close()
        }
}

private fun extractCharacterId(
    rawValue: String
): Int? {

    return try {

        val uri =
            Uri.parse(
                rawValue
            )

        if (
            uri.scheme !=
            "rickmorty" ||
            uri.host !=
            "character"
        ) {

            null

        } else {

            uri.pathSegments
                .firstOrNull()
                ?.toIntOrNull()
                ?.takeIf {

                    it > 0
                }
        }

    } catch (
        e: Exception
    ) {

        null
    }
}