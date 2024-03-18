package com.hightech.cryptoapp

import com.hightech.cryptoapp.infra.LocalAvengerStore
import com.hightech.cryptoapp.local.AvengerStore

class LocalAvengerStoreFactory {
    companion object {
        fun createLocalAvengerStore(): AvengerStore {
            return LocalAvengerStore()
        }
    }
}