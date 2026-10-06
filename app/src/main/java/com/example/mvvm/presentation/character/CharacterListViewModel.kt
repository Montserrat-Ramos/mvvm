package com.example.mvvm.presentation.character

import androidx.lifecycle.ViewModel
import com.example.mvvm.data.CharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class  CharacterListViewModel: ViewModel() {
    //Mandar a llamar las vista UI para el model
    private val _uiState = MutableStateFlow(
        CharacterListUiState()
    )

    val uiState: StateFlow<CharacterListUiState> =
        _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters(){
        val characters = listOf(
            CharacterModel(
                id = 1,
                name = "Ricky Sanchez",
                status = "Alive",
                species = "Human",
                image = ""
            ),
            CharacterModel(
                id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                image = ""
            ),
            CharacterModel(
                id = 3,
                name = "Summer Smith",
                status = "Alive",
                species = "Human",
                image = ""
            ),
        )

        _uiState.value = CharacterListUiState(
            characterModel = characters)
    }
}