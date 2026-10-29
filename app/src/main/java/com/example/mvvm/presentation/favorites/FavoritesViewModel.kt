package com.example.mvvm.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(

    private val favoriteRepository:
    FavoriteRepository

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            FavoritesUiState()
        )

    val uiState:
            StateFlow<FavoritesUiState> =
        _uiState.asStateFlow()

    init {

        observeFavorites()
    }

    private fun observeFavorites() {

        viewModelScope.launch {

            try {

                favoriteRepository
                    .getFavorites()
                    .collect { favorites ->

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                favorites =
                                    favorites,
                                error = null
                            )
                        }
                    }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error =
                            e.message
                                ?: "Error al obtener favoritos."
                    )
                }
            }
        }
    }
}