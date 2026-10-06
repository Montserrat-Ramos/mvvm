package com.example.mvvm.presentation.character

import com.example.mvvm.data.CharacterModel

data class CharacterListUiState(
    val title: String = "Personajes de Ricky and Morty",
    val isLoading: Boolean = false,
    val characterModel: List<CharacterModel> = emptyList(),
    val error: String? = null
)