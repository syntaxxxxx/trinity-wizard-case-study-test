package com.hightech.cryptoapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hightech.cryptoapp.LoadAvengerLocalUseCaseFactory.Companion.createLoadAvengerLocalUseCase
import com.hightech.cryptoapp.avenger.presentation.AvengerViewModel

val FACTORY: ViewModelProvider.Factory = viewModelFactory {
    initializer {
        AvengerViewModel(
            useCase = createLoadAvengerLocalUseCase()
        )
    }
}