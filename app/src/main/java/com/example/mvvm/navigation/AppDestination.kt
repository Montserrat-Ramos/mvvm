package com.example.mvvm.navigation

sealed class AppDestination(
    val route: String,
    val title: String
) {

    data object Characters :
        AppDestination(
            route = "characters",
            title = "Personajes"
        )

    data object Favorites :
        AppDestination(
            route = "favorites",
            title = "Favoritos"
        )

    data object About :
        AppDestination(
            route = "about",
            title = "Acerca de"
        )

    data object Detail :
        AppDestination(
            route = "detail/{characterId}",
            title = "Detalle"
        ) {

        fun createRoute(
            characterId: Int
        ): String {

            return "detail/$characterId"
        }
    }

    data object QrScanner :
        AppDestination(
            route = "qr_scanner",
            title = "Escanear QR"
        )

    data object Camera :
        AppDestination(
            route = "camera",
            title = "Cámara"
        )
}