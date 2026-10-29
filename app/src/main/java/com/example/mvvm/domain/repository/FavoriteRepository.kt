package com.example.mvvm.domain.repository

import com.example.mvvm.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavorites():
            Flow<List<CharacterModel>>

    fun isFavorite(
        characterId: Int
    ): Flow<Boolean>

    suspend fun addFavorite(
        character: CharacterModel
    )

    suspend fun removeFavorite(
        characterId: Int
    )
}