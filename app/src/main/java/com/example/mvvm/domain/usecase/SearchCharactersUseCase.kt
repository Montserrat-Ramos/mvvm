package com.example.mvvm.domain.usecase

import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.domain.model.CharacterPage
import com.example.mvvm.domain.repository.CharacterRepository
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int
    ): CharacterPage {

        return repository.searchCharacters(
            query = query,
            page = page
        )
    }
}