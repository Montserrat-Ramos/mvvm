package com.example.mvvm.core.qr

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

object QrGenerator {

    fun generate(
        content: String,
        size: Int = 900
    ): Bitmap {

        val hints = mapOf(
            EncodeHintType.CHARACTER_SET to "UTF-8",
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
            EncodeHintType.MARGIN to 1
        )

        val bitMatrix =
            MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                size,
                size,
                hints
            )

        val pixels =
            IntArray(
                size * size
            )

        for (y in 0 until size) {

            for (x in 0 until size) {

                pixels[
                    y * size + x
                ] =
                    if (
                        bitMatrix[x, y]
                    ) {
                        Color.rgb(
                            23,
                            32,
                            38
                        )
                    } else {
                        Color.WHITE
                    }
            }
        }

        return Bitmap.createBitmap(
            size,
            size,
            Bitmap.Config.ARGB_8888
        ).apply {

            setPixels(
                pixels,
                0,
                size,
                0,
                0,
                size,
                size
            )
        }
    }
}