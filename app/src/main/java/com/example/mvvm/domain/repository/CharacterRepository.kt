package com.example.mvvm.domain.repository

import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.domain.model.CharacterPage

interface CharacterRepository {

    suspend fun getCharacters(
        page: Int
    ): CharacterPage

    suspend fun getCharacter(
        id: Int
    ): CharacterModel

    suspend fun searchCharacters(
        query: String,
        page: Int
    ): CharacterPage
}