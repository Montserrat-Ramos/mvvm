package com.example.mvvm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_characters"
)
data class FavoriteCharacterEntity(

    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val gender: String,
    val origin: String,
    val location: String
)