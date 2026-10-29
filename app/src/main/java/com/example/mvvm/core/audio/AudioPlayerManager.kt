package com.example.mvvm.core.audio

import android.media.MediaPlayer
import java.io.File

class AudioPlayerManager {

    private var mediaPlayer:
            MediaPlayer? = null

    fun play(
        file: File,
        onCompletion: () -> Unit,
        onError: () -> Unit
    ): Boolean {

        if (!file.exists()) {
            return false
        }

        stop()

        return try {

            mediaPlayer =
                MediaPlayer().apply {

                    setDataSource(
                        file.absolutePath
                    )

                    setOnCompletionListener {

                        stop()

                        onCompletion()
                    }

                    setOnErrorListener {
                            _,
                            _,
                            _ ->

                        stop()

                        onError()

                        true
                    }

                    prepare()

                    start()
                }

            true

        } catch (e: Exception) {

            e.printStackTrace()

            stop()

            false
        }
    }

    fun stop() {

        try {

            mediaPlayer
                ?.takeIf {
                    it.isPlaying
                }
                ?.stop()

        } catch (
            e: Exception
        ) {

            e.printStackTrace()
        }

        try {

            mediaPlayer?.release()

        } catch (
            e: Exception
        ) {

            e.printStackTrace()
        }

        mediaPlayer = null
    }

    fun release() {

        stop()
    }
}