package com.example.mvvm.core.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore

object ImageStorageManager {

    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): Uri? {

        val contentResolver =
            context.contentResolver

        val values =
            ContentValues().apply {

                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    fileName
                )

                put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/png"
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
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
                ?: return null

        return try {

            contentResolver
                .openOutputStream(uri)
                ?.use { outputStream ->

                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    )
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

                contentResolver.update(
                    uri,
                    values,
                    null,
                    null
                )
            }

            uri

        } catch (e: Exception) {

            contentResolver.delete(
                uri,
                null,
                null
            )

            null
        }
    }
}