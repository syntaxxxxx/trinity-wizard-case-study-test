package com.hightech.cryptoapp.presentation

data class UiState(
    val isLoading: Boolean = false,
    val avengers: List<AvengerItemViewModel> = emptyList(),
    val failed: String = "",
)