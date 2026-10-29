package com.example.mvvm.presentation.favorites

import com.example.mvvm.domain.model.CharacterModel

data class FavoritesUiState(

    val isLoading: Boolean = true,

    /*val characters: List<CharacterModel> =
        emptyList(),*/
    val favorites: List<CharacterModel> = emptyList(),

    val error: String? = null
)