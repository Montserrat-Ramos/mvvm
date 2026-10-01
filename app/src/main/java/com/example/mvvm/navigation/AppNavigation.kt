package com.example.mvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm.presentation.character.CharacterDetailScreen
import com.example.mvvm.presentation.characters.CharacterListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "characters"
    ) {

        composable("characters") {
            CharacterListScreen(
                navController = navController
            )
        }

        composable("detail") {
            CharacterDetailScreen()
        }
    }
}