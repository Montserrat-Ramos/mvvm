package com.example.mvvm.presentation.character

import com.example.mvvm.data.CharacterModel

data class CharacterListUiState(
    val title: String = "Personajes de Ricky and Morty",
    val isLoading: Boolean = false,
    val characterModel: List<CharacterModel> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
) {
    val characters: List<CharacterModel>
        get() = if (searchQuery.isBlank()) {
            characterModel
        } else {
            characterModel.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
}
