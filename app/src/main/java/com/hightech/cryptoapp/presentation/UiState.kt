package com.hightech.cryptoapp.presentation

data class UiState(
    val isLoading: Boolean = false,
    val avenger: List<AvengerItemViewModel> = emptyList(),
    val failed: String = "",
)