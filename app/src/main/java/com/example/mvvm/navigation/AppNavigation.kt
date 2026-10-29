package com.example.mvvm.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvm.presentation.about.AboutScreen
import com.example.mvvm.presentation.camera.CameraScreen
import com.example.mvvm.presentation.characters.CharacterListScreen
import com.example.mvvm.presentation.components.AppBottomBar
import com.example.mvvm.presentation.detail.CharacterDetailScreen
import com.example.mvvm.presentation.favorites.FavoritesScreen
import com.example.mvvm.presentation.qr.QrScannerScreen

@Composable
fun AppNavigation(
    initialCharacterId: Int? = null
) {

    val navController =
        rememberNavController()

    val backStackEntry by
    navController
        .currentBackStackEntryAsState()

    val currentRoute =
        backStackEntry
            ?.destination
            ?.route

    var widgetNavigationConsumed
            by rememberSaveable(
                initialCharacterId
            ) {

                mutableStateOf(
                    false
                )
            }

    LaunchedEffect(
        initialCharacterId
    ) {

        if (
            initialCharacterId != null &&
            initialCharacterId > 0 &&
            !widgetNavigationConsumed
        ) {

            widgetNavigationConsumed =
                true

            navController.navigate(
                AppDestination
                    .Detail
                    .createRoute(
                        initialCharacterId
                    )
            ) {
                launchSingleTop = true
            }
        }
    }

    val showBottomBar =
        currentRoute ==
                AppDestination
                    .Characters
                    .route ||

                currentRoute ==
                AppDestination
                    .Favorites
                    .route ||

                currentRoute ==
                AppDestination
                    .About
                    .route ||

                currentRoute ==
                AppDestination
                    .Detail
                    .route

    Scaffold(

        bottomBar = {

            if (showBottomBar) {

                AppBottomBar(
                    navController =
                        navController
                )
            }
        }

    ) { innerPadding ->

        NavHost(

            navController =
                navController,

            startDestination =
                AppDestination
                    .Characters
                    .route,

            modifier =
                Modifier.padding(
                    innerPadding
                )
        ) {
            composable(
                route =
                    AppDestination
                        .Characters
                        .route
            ) {

                CharacterListScreen(
                    navController =
                        navController
                )
            }
            composable(
                route =
                    AppDestination
                        .Favorites
                        .route
            ) {

                FavoritesScreen(
                    navController =
                        navController
                )
            }

            composable(
                route =
                    AppDestination
                        .About
                        .route
            ) {

                AboutScreen()
            }

            composable(
                route =
                    AppDestination
                        .QrScanner
                        .route
            ) {

                QrScannerScreen(
                    navController =
                        navController
                )
            }
            composable(
                route =
                    AppDestination
                        .Camera
                        .route
            ) {

                CameraScreen(
                    navController =
                        navController
                )
            }

            composable(

                route =
                    AppDestination
                        .Detail
                        .route,

                arguments =
                    listOf(

                        navArgument(
                            "characterId"
                        ) {

                            type =
                                NavType.IntType
                        }
                    )

            ) { backStackEntry ->

                val characterId =
                    backStackEntry
                        .arguments
                        ?.getInt(
                            "characterId"
                        )
                        ?: return@composable

                CharacterDetailScreen(

                    characterId =
                        characterId,

                    navController =
                        navController
                )
            }
        }
    }
}