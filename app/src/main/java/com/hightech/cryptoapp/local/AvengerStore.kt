package com.hightech.cryptoapp.local

import com.hightech.cryptoapp.domain.AvengerResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AvengerStore {
    fun get(): Flow<Exception>
}

class LoadAvengerLocalUseCase(private val store: AvengerStore) {
    fun load(): Flow<AvengerResult> = flow {
        store.get()
    }
}