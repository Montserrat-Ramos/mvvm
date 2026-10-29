package com.example.mvvm.data.repository

import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.data.remote.api.RickMortyApi
import com.example.mvvm.data.remote.dto.toDomain
import com.example.mvvm.domain.model.CharacterPage
import com.example.mvvm.domain.repository.CharacterRepository
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickMortyApi
) : CharacterRepository {

    override suspend fun getCharacters(
        page: Int
    ): CharacterPage {

        val response = api.getCharacters(page)

        return CharacterPage(
            characters = response.results.map { dto ->
                dto.toDomain()
            },
            currentPage = page,
            totalPages = response.info.pages,
            hasNextPage = response.info.next != null
        )
    }

    override suspend fun searchCharacters(
        query: String,
        page: Int
    ): CharacterPage {

        return try {

            val response = api.searchCharacters(
                name = query,
                page = page
            )

            CharacterPage(
                characters = response.results.map { dto ->
                    dto.toDomain()
                },
                currentPage = page,
                totalPages = response.info.pages,
                hasNextPage = response.info.next != null
            )

        } catch (e: HttpException) {

            if (e.code() == 404) {

                CharacterPage(
                    characters = emptyList(),
                    currentPage = page,
                    totalPages = 0,
                    hasNextPage = false
                )

            } else {
                throw e
            }
        }
    }

    override suspend fun getCharacter(
        id: Int
    ): CharacterModel {

        return api
            .getCharacter(id)
            .toDomain()
    }
}