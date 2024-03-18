package com.hightech.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hightech.cryptoapp.domain.AvengerResult
import com.hightech.cryptoapp.domain.LoadAvengerUseCase
import com.hightech.cryptoapp.domain.Unexpected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvengerViewModel(private val useCase: LoadAvengerUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadAvengers() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            useCase.load().collect { result ->
                _uiState.update {
                    when(result) {
                        is AvengerResult.Success -> TODO()

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
}
