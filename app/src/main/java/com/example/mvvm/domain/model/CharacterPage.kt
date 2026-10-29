package com.example.mvvm.domain.model

data class CharacterPage(
    val characters: List<CharacterModel>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean
)