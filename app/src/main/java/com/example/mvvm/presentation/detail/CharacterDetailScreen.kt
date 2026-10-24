package com.example.mvvm.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController
) {
    Text(text = "Detalle del personaje")
}