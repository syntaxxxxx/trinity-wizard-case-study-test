package com.hightech.cryptoapp

import com.hightech.cryptoapp.LocalAvengerStoreFactory.Companion.createLocalAvengerStore
import com.hightech.cryptoapp.avenger.domain.LoadAvengerUseCase
import com.hightech.cryptoapp.avenger.local.LoadAvengerLocalUseCase

class LoadAvengerLocalUseCaseFactory {
    companion object {
        fun createLoadAvengerLocalUseCase(): LoadAvengerUseCase {
            return LoadAvengerLocalUseCase(
                store = createLocalAvengerStore()
            )
        }
    }
}