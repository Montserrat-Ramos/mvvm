package com.example.mvvm.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.model.CharacterModel
import com.example.mvvm.domain.usecase.GetCharactersUseCase
import com.example.mvvm.domain.usecase.SearchCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(

    private val getCharactersUseCase: GetCharactersUseCase,

    private val searchCharactersUseCase: SearchCharactersUseCase

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            CharacterListUiState()
        )

    val uiState: StateFlow<CharacterListUiState> =
        _uiState.asStateFlow()

    private var searchJob: Job? = null

    private var loadedCharacters:
            List<CharacterModel> = emptyList()

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    currentPage = 1
                )
            }

            try {

                val page =
                    getCharactersUseCase(
                        page = 1
                    )

                loadedCharacters =
                    page.characters

                val filtered =
                    applyFilter(
                        loadedCharacters,
                        _uiState.value.selectedFilter
                    )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        characters = filtered,
                        currentPage =
                            page.currentPage,
                        totalPages =
                            page.totalPages,
                        hasNextPage =
                            page.hasNextPage,
                        error = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                            ?: "No fue posible obtener los personajes."
                    )
                }
            }
        }
    }

    fun loadNextPage() {

        val state =
            _uiState.value

        if (
            state.isLoading ||
            state.isLoadingMore ||
            !state.hasNextPage
        ) {
            return
        }

        val nextPage =
            state.currentPage + 1

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoadingMore = true
                )
            }

            try {

                val page =
                    if (
                        state.searchQuery
                            .isBlank()
                    ) {

                        getCharactersUseCase(
                            page = nextPage
                        )

                    } else {

                        searchCharactersUseCase(
                            query =
                                state.searchQuery,
                            page =
                                nextPage
                        )
                    }

                loadedCharacters =
                    loadedCharacters +
                            page.characters

                val filtered =
                    applyFilter(
                        loadedCharacters,
                        state.selectedFilter
                    )

                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        characters = filtered,
                        currentPage =
                            page.currentPage,
                        totalPages =
                            page.totalPages,
                        hasNextPage =
                            page.hasNextPage,
                        error = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        error = e.message
                            ?: "No fue posible cargar más personajes."
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(
        query: String
    ) {

        _uiState.update {
            it.copy(
                searchQuery = query
            )
        }

        searchJob?.cancel()

        if (query.isBlank()) {

            loadFirstPage()

            return
        }

        searchJob =
            viewModelScope.launch {

                delay(400)

                searchFirstPage(query)
            }
    }

    private suspend fun searchFirstPage(
        query: String
    ) {

        _uiState.update {
            it.copy(
                isLoading = true,
                error = null,
                currentPage = 1
            )
        }

        try {

            val page =
                searchCharactersUseCase(
                    query = query,
                    page = 1
                )

            loadedCharacters =
                page.characters

            val filtered =
                applyFilter(
                    loadedCharacters,
                    _uiState.value
                        .selectedFilter
                )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    characters = filtered,
                    currentPage =
                        page.currentPage,
                    totalPages =
                        page.totalPages,
                    hasNextPage =
                        page.hasNextPage,
                    error = null
                )
            }

        } catch (e: Exception) {

            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = e.message
                        ?: "Error al buscar personajes."
                )
            }
        }
    }

    fun onFilterSelected(
        filter: CharacterFilter
    ) {

        val filtered =
            applyFilter(
                loadedCharacters,
                filter
            )

        _uiState.update {
            it.copy(
                selectedFilter = filter,
                characters = filtered
            )
        }
    }

    private fun applyFilter(
        characters: List<CharacterModel>,
        filter: CharacterFilter
    ): List<CharacterModel> {

        return when (filter) {

            CharacterFilter.ALL -> {
                characters
            }

            CharacterFilter.ALIVE -> {
                characters.filter {
                    it.status.equals(
                        "Alive",
                        ignoreCase = true
                    )
                }
            }

            CharacterFilter.DEAD -> {
                characters.filter {
                    it.status.equals(
                        "Dead",
                        ignoreCase = true
                    )
                }
            }
        }
    }

    fun retry() {

        val query =
            _uiState.value.searchQuery

        if (query.isBlank()) {

            loadFirstPage()

        } else {

            viewModelScope.launch {
                searchFirstPage(query)
            }
        }
    }

    fun getCharactersForExport():
            List<CharacterModel> {

        return _uiState.value.characters
    }
}