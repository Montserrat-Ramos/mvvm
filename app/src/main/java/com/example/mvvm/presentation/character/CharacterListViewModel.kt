package com.example.mvvm.presentation.character

import androidx.lifecycle.ViewModel
import com.example.mvvm.data.CharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterListViewModel : ViewModel() {

    private val allCharacters = listOf(
        CharacterModel(
            id = 1,
            name = "Rick Sanchez",
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
        CharacterModel(
            id = 4,
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            image = ""
        ),
        CharacterModel(
            id = 5,
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            image = ""
        )
    )

    private val _uiState = MutableStateFlow(
        CharacterListUiState(characters = allCharacters)
    )
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        _uiState.update { currentState ->
            currentState.copy(
                characters = allCharacters,
                isLoading = false
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        val filteredList = if (query.isBlank()) {
            allCharacters
        } else {
            allCharacters.filter { character ->
                character.name.contains(query, ignoreCase = true) ||
                        character.species.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                characters = filteredList
            )
        }
    }
}
