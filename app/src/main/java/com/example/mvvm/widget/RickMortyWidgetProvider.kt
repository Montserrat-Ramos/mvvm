package com.example.mvvm.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.RemoteViews
import com.example.mvvm.MainActivity
import com.example.mvvm.R
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class RickMortyWidgetProvider :
    AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        appWidgetIds.forEach { appWidgetId ->

            updateWidget(
                context = context,
                appWidgetManager = appWidgetManager,
                appWidgetId = appWidgetId
            )
        }
    }

    companion object {

        private val executor =
            Executors.newSingleThreadExecutor()

        fun updateAll(
            context: Context
        ) {

            val appWidgetManager =
                AppWidgetManager.getInstance(
                    context
                )

            val componentName =
                ComponentName(
                    context,
                    RickMortyWidgetProvider::class.java
                )

            val appWidgetIds =
                appWidgetManager.getAppWidgetIds(
                    componentName
                )

            appWidgetIds.forEach { appWidgetId ->

                updateWidget(
                    context = context,
                    appWidgetManager = appWidgetManager,
                    appWidgetId = appWidgetId
                )
            }
        }

        private fun updateWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val views =
                RemoteViews(
                    context.packageName,
                    R.layout.widget_rick_morty
                )

            val character =
                WidgetCharacterStore.getCharacter(
                    context
                )

            if (character != null) {

                views.setTextViewText(
                    R.id.tvWidgetCharacterName,
                    character.name
                )

                views.setTextViewText(
                    R.id.tvWidgetStatus,
                    character.status
                )

                views.setTextViewText(
                    R.id.tvWidgetSpecies,
                    character.species
                )

                when (
                    character.status.lowercase()
                ) {

                    "alive" -> {

                        views.setTextColor(
                            R.id.tvWidgetStatus,
                            Color.parseColor(
                                "#14752A"
                            )
                        )

                        views.setImageViewResource(
                            R.id.ivWidgetStatus,
                            R.drawable.widget_status_alive
                        )
                    }

                    "dead" -> {

                        views.setTextColor(
                            R.id.tvWidgetStatus,
                            Color.parseColor(
                                "#B32E37"
                            )
                        )

                        views.setImageViewResource(
                            R.id.ivWidgetStatus,
                            R.drawable.widget_status_dead
                        )
                    }

                    else -> {

                        views.setTextColor(
                            R.id.tvWidgetStatus,
                            Color.parseColor(
                                "#65717A"
                            )
                        )

                        views.setImageViewResource(
                            R.id.ivWidgetStatus,
                            R.drawable.widget_status_unknown
                        )
                    }
                }

                val intent =
                    Intent(
                        context,
                        MainActivity::class.java
                    ).apply {

                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP

                        putExtra(
                            "widget_character_id",
                            character.id
                        )
                    }

                val pendingIntent =
                    PendingIntent.getActivity(
                        context,
                        character.id,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or
                                PendingIntent.FLAG_IMMUTABLE
                    )

                views.setOnClickPendingIntent(
                    R.id.btnWidgetOpen,
                    pendingIntent
                )

                views.setOnClickPendingIntent(
                    R.id.widgetCharacterContent,
                    pendingIntent
                )

                views.setOnClickPendingIntent(
                    R.id.ivWidgetCharacter,
                    pendingIntent
                )

                appWidgetManager.updateAppWidget(
                    appWidgetId,
                    views
                )

                if (
                    character.image.isNotBlank()
                ) {

                    executor.execute {

                        try {

                            val url =
                                URL(
                                    character.image
                                )

                            val connection =
                                url.openConnection()
                                        as HttpURLConnection

                            connection.doInput =
                                true

                            connection.connectTimeout =
                                10000

                            connection.readTimeout =
                                10000

                            connection.connect()

                            val bitmap =
                                connection
                                    .inputStream
                                    .use {
                                            stream ->

                                        BitmapFactory
                                            .decodeStream(
                                                stream
                                            )
                                    }

                            connection.disconnect()

                            if (bitmap != null) {

                                val imageViews =
                                    RemoteViews(
                                        context.packageName,
                                        R.layout.widget_rick_morty
                                    )

                                imageViews.setImageViewBitmap(
                                    R.id.ivWidgetCharacter,
                                    bitmap
                                )

                                appWidgetManager.updateAppWidget(
                                    appWidgetId,
                                    imageViews
                                )
                            }

                        } catch (
                            e: Exception
                        ) {

                            e.printStackTrace()
                        }
                    }
                }

            } else {

                views.setTextViewText(
                    R.id.tvWidgetCharacterName,
                    "Selecciona un personaje"
                )

                views.setTextViewText(
                    R.id.tvWidgetStatus,
                    "Multiverso"
                )

                views.setTextViewText(
                    R.id.tvWidgetSpecies,
                    "Rick & Morty"
                )

                views.setImageViewResource(
                    R.id.ivWidgetStatus,
                    R.drawable.widget_status_unknown
                )

                views.setImageViewResource(
                    R.id.ivWidgetCharacter,
                    R.drawable.portal_vortex
                )

                appWidgetManager.updateAppWidget(
                    appWidgetId,
                    views
                )
            }
        }
    }
}