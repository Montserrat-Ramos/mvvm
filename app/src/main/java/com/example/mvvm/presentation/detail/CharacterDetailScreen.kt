package com.example.mvvm.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController
) {
    val character = getCharacterById(characterId)

    Column(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.AutoMirrored
                    .Filled.ArrowBack,
                    contentDescription = "Regresar")
            }
            Text(text = "Detalle del personaje",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CharacterDetailAvatar(
                    name = character.name,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    //Text(text = "Detalle del personaje")
    //Text(text = "ID: $characterId")
}

fun getCharacterById(characterId: Int) {}

@Composable
fun CharacterDetailAvatar(name: String) {
    val initials = name
        .split(" ")
        .take( n = 2).mapNotNull {
            word -> word.firstOrNull()
                ?.uppercaseChar()
        }.joinToString ( separator = "" )
    Box(modifier = Modifier.size(150.dp)
        .clip(CircleShape).background(
            color = MaterialTheme
                .colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ){
        Text(text = initials
            //modifier = Modifier.fillMaxWidth()
            , fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}