package com.example.mvvm.data.repository

import com.example.mvvm.data.local.dao.FavoriteCharacterDao
import com.example.mvvm.data.local.mapper.toDomain
import com.example.mvvm.data.local.mapper.toFavoriteEntity
import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(

    private val dao:
    FavoriteCharacterDao

) : FavoriteRepository {

    override fun getFavorites():
            Flow<List<CharacterModel>> {

        return dao
            .getAllFavorites()
            .map { favorites ->

                favorites.map {
                    it.toDomain()
                }
            }
    }

    override fun isFavorite(
        characterId: Int
    ): Flow<Boolean> {

        return dao.isFavorite(
            characterId
        )
    }

    override suspend fun addFavorite(
        character: CharacterModel
    ) {

        dao.insert(
            character
                .toFavoriteEntity()
        )
    }

    override suspend fun removeFavorite(
        characterId: Int
    ) {

        dao.deleteById(
            characterId
        )
    }
}