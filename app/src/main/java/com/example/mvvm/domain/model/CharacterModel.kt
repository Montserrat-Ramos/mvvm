package com.example.mvvm.domain.model

data class CharacterModel (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val gender: String = "unknown",
    val origin: String = "unknown",
    val location: String = "unknown"
)
