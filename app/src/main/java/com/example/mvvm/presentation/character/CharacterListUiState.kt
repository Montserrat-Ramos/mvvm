package com.example.mvvm.presentation.character

import com.example.mvvm.domain.model.CharacterModel

data class CharacterListUiState(
    /*val title: String = "Personajes de Ricky and Morty",
    val isLoading: Boolean = false,
    //val characterModel: List<CharacterModel> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null,
    val characters: List<CharacterModel>*/
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val characters: List<CharacterModel> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: CharacterFilter =
        CharacterFilter.ALL,
    val error: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val hasNextPage: Boolean = false
)