package com.hightech.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hightech.cryptoapp.domain.LoadAvengerUseCase
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

            useCase.load()
        }
    }
}
