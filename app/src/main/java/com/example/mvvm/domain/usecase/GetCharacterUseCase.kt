package com.example.mvvm.domain.usecase

import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    suspend operator fun invoke(
        id: Int
    ): CharacterModel {

        return repository.getCharacter(id)
    }
}