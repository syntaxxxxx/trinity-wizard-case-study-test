package com.hightech.cryptoapp.avenger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hightech.cryptoapp.avenger.domain.Avenger
import com.hightech.cryptoapp.avenger.domain.AvengerResult
import com.hightech.cryptoapp.avenger.domain.LoadAvengerUseCase
import com.hightech.cryptoapp.avenger.domain.Unexpected
import com.hightech.cryptoapp.avenger.local.LocalAvenger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvengerViewModel(private val useCase: LoadAvengerUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadAvengers()
    }

    fun loadAvengers() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            useCase.load().collect { result ->
                _uiState.update {
                    when(result) {
                        is AvengerResult.Success -> {
                            it.copy(
                                isLoading = false,
                                avengers = result.avengers.toModels()
                            )
                        }

                        is AvengerResult.Failure -> {
                            it.copy(
                                isLoading = false,
                                failed = when(result.exception) {
                                    is Unexpected -> {
                                        "Unexpected Error"
                                    }

                                    else -> "Something Went Wrong"
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    private fun List<Avenger>.toModels(): List<AvengerItemViewModel> {
        return map {
            AvengerItemViewModel(
                id = it.id,
                name = it.name,
                rating = it.rating,
                image = it.image
            )
        }
    }
}
