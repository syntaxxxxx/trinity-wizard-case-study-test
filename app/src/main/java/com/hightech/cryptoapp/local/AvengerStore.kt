package com.hightech.cryptoapp.local

interface AvengerStore {
    fun get()
}

class LoadAvengerLocalUseCase(private val store: AvengerStore) {

}