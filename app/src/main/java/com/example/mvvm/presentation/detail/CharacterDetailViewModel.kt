package com.example.mvvm.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.repository.FavoriteRepository
import com.example.mvvm.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(

    private val getCharacterUseCase: GetCharacterUseCase,

    private val favoriteRepository: FavoriteRepository

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            CharacterDetailUiState()
        )

    val uiState: StateFlow<CharacterDetailUiState> =
        _uiState.asStateFlow()

    private var favoriteJob: Job? = null

    fun loadCharacter(
        characterId: Int
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {

                val character =
                    getCharacterUseCase(
                        characterId
                    )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        character = character,
                        error = null
                    )
                }

                observeFavorite(
                    characterId
                )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                            ?: "No fue posible obtener el personaje."
                    )
                }
            }
        }
    }

    private fun observeFavorite(
        characterId: Int
    ) {

        favoriteJob?.cancel()

        favoriteJob =
            viewModelScope.launch {

                favoriteRepository
                    .isFavorite(
                        characterId
                    )
                    .collect { isFavorite ->

                        _uiState.update {
                            it.copy(
                                isFavorite =
                                    isFavorite
                            )
                        }
                    }
            }
    }

    fun toggleFavorite() {

        val character =
            _uiState.value.character
                ?: return

        val currentlyFavorite =
            _uiState.value.isFavorite

        viewModelScope.launch {

            if (currentlyFavorite) {

                favoriteRepository
                    .removeFavorite(
                        character.id
                    )

            } else {

                favoriteRepository
                    .addFavorite(
                        character
                    )
            }
        }
    }
}