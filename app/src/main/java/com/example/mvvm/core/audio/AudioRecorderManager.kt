package com.example.mvvm.core.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioRecorderManager {

    private var recorder: MediaRecorder? = null

    fun startRecording(
        context: Context,
        outputFile: File
    ): Boolean {

        return try {

            outputFile
                .parentFile
                ?.mkdirs()

            if (outputFile.exists()) {
                outputFile.delete()
            }

            recorder =
                if (
                    Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.S
                ) {

                    MediaRecorder(context)

                } else {

                    @Suppress("DEPRECATION")
                    MediaRecorder()
                }

            recorder?.apply {

                setAudioSource(
                    MediaRecorder.AudioSource.MIC
                )

                setOutputFormat(
                    MediaRecorder.OutputFormat.MPEG_4
                )

                setAudioEncoder(
                    MediaRecorder.AudioEncoder.AAC
                )

                setAudioEncodingBitRate(
                    128000
                )

                setAudioSamplingRate(
                    44100
                )

                setOutputFile(
                    outputFile.absolutePath
                )

                prepare()

                start()
            }

            true

        } catch (e: Exception) {

            e.printStackTrace()

            release()

            false
        }
    }

    fun stopRecording(): Boolean {

        val currentRecorder =
            recorder
                ?: return false

        return try {

            currentRecorder.stop()

            true

        } catch (e: RuntimeException) {

            e.printStackTrace()

            false

        } finally {

            release()
        }
    }

    fun release() {

        try {

            recorder?.release()

        } catch (
            e: Exception
        ) {

            e.printStackTrace()
        }

        recorder = null
    }
}