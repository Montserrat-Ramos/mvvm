package com.example.mvvm.presentation.detail

import com.example.mvvm.domain.model.CharacterModel

data class CharacterDetailUiState(
    val isLoading: Boolean = false,
    val character: CharacterModel? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)