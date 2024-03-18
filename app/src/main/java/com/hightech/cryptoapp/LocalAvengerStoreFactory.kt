package com.hightech.cryptoapp

import com.hightech.cryptoapp.avenger.infra.LocalAvengerStore
import com.hightech.cryptoapp.avenger.local.AvengerStore

class LocalAvengerStoreFactory {
    companion object {
        fun createLocalAvengerStore(): AvengerStore {
            return LocalAvengerStore()
        }
    }
}