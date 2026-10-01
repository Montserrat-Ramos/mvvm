package com.example.mvvm.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CharacterListScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Lista de personajes")

        Button (
            onClick = {
                navController.navigate("detail")
            }
        ) {
            Text(text = "Ver detalle")
        }
    }
}