package com.example.mvvm.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mvvm.navigation.AppDestination

private val MultiverseGreen =
    Color(0xFF83D52E)

private val MultiverseDark =
    Color(0xFF062432)

private val MultiverseGray =
    Color(0xFF65717A)

private val MultiversePurple =
    Color(0xFFEBDDFF)

@Composable
fun AppBottomBar(
    navController: NavController
) {

    val navBackStackEntry by
    navController
        .currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route

    val selectedRoute =
        when {

            currentRoute ==
                    AppDestination
                        .Detail
                        .route -> {

                AppDestination
                    .Characters
                    .route
            }

            else ->
                currentRoute
        }

    NavigationBar(
        containerColor =
            Color.White.copy(
                alpha = 0.96f
            ),

        tonalElevation =
            4.dp
    ) {

        NavigationBarItem(
            selected =
                selectedRoute ==
                        AppDestination
                            .Characters
                            .route,

            onClick = {

                navController.navigate(
                    AppDestination
                        .Characters
                        .route
                ) {

                    popUpTo(
                        AppDestination
                            .Characters
                            .route
                    ) {

                        saveState =
                            true
                    }

                    launchSingleTop =
                        true

                    restoreState =
                        true
                }
            },

            icon = {

                Icon(
                    imageVector =
                        if (
                            selectedRoute ==
                            AppDestination
                                .Characters
                                .route
                        ) {

                            Icons.Filled
                                .Person

                        } else {

                            Icons.Outlined
                                .Person
                        },

                    contentDescription =
                        "Personajes"
                )
            },

            label = {

                Text(
                    text =
                        "Personajes",

                    fontWeight =
                        if (
                            selectedRoute ==
                            AppDestination
                                .Characters
                                .route
                        ) {

                            FontWeight.Bold

                        } else {

                            FontWeight.Medium
                        }
                )
            },

            colors =
                NavigationBarItemDefaults
                    .colors(

                        selectedIconColor =
                            MultiverseGreen,

                        selectedTextColor =
                            MultiverseDark,

                        indicatorColor =
                            MultiversePurple,

                        unselectedIconColor =
                            MultiverseGray,

                        unselectedTextColor =
                            MultiverseGray
                    )
        )

        NavigationBarItem(
            selected =
                selectedRoute ==
                        AppDestination
                            .Favorites
                            .route,

            onClick = {

                navController.navigate(
                    AppDestination
                        .Favorites
                        .route
                ) {

                    popUpTo(
                        AppDestination
                            .Characters
                            .route
                    ) {

                        saveState =
                            true
                    }

                    launchSingleTop =
                        true

                    restoreState =
                        true
                }
            },

            icon = {

                Icon(
                    imageVector =
                        if (
                            selectedRoute ==
                            AppDestination
                                .Favorites
                                .route
                        ) {

                            Icons.Filled
                                .Favorite

                        } else {

                            Icons.Outlined
                                .FavoriteBorder
                        },

                    contentDescription =
                        "Favoritos"
                )
            },

            label = {

                Text(
                    text =
                        "Favoritos",

                    fontWeight =
                        if (
                            selectedRoute ==
                            AppDestination
                                .Favorites
                                .route
                        ) {

                            FontWeight.Bold

                        } else {

                            FontWeight.Medium
                        }
                )
            },

            colors =
                NavigationBarItemDefaults
                    .colors(

                        selectedIconColor =
                            Color(
                                0xFFE94B64
                            ),

                        selectedTextColor =
                            MultiverseDark,

                        indicatorColor =
                            Color(
                                0xFFF7DDE5
                            ),

                        unselectedIconColor =
                            MultiverseGray,

                        unselectedTextColor =
                            MultiverseGray
                    )
        )

        NavigationBarItem(
            selected =
                selectedRoute ==
                        AppDestination
                            .About
                            .route,

            onClick = {

                navController.navigate(
                    AppDestination
                        .About
                        .route
                ) {

                    popUpTo(
                        AppDestination
                            .Characters
                            .route
                    ) {

                        saveState =
                            true
                    }

                    launchSingleTop =
                        true

                    restoreState =
                        true
                }
            },

            icon = {

                Icon(
                    imageVector =
                        if (
                            selectedRoute ==
                            AppDestination
                                .About
                                .route
                        ) {

                            Icons.Filled
                                .Info

                        } else {

                            Icons.Outlined
                                .Info
                        },

                    contentDescription =
                        "Acerca de"
                )
            },

            label = {

                Text(
                    text =
                        "Acerca de",

                    fontWeight =
                        if (
                            selectedRoute ==
                            AppDestination
                                .About
                                .route
                        ) {

                            FontWeight.Bold

                        } else {

                            FontWeight.Medium
                        }
                )
            },

            colors =
                NavigationBarItemDefaults
                    .colors(

                        selectedIconColor =
                            Color(
                                0xFF684098
                            ),

                        selectedTextColor =
                            MultiverseDark,

                        indicatorColor =
                            MultiversePurple,

                        unselectedIconColor =
                            MultiverseGray,

                        unselectedTextColor =
                            MultiverseGray
                    )
        )
    }
}