package com.hightech.cryptoapp.presentation

import com.hightech.cryptoapp.domain.LoadAvengerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AvengerViewModel(private val useCase: LoadAvengerUseCase) {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadAvengers() {
        useCase.load()
    }
}
