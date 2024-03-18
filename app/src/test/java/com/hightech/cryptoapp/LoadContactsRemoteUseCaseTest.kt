package com.hightech.cryptoapp

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

interface AvengerStore {
    fun get()
}

class LoadAvengerLocalUseCase(private val store: AvengerStore) {

}

class LoadAvengerRemoteUseCaseTest {
    private val store = spyk<AvengerStore>()
    private lateinit var sut: LoadAvengerLocalUseCase

    @Before
    fun setUp() {
        sut = LoadAvengerLocalUseCase(store = store)
    }

    @Test
    fun testInitDoesNotRequestData() {
        verify(exactly = 0) {
            store.get()
        }

        confirmVerified(store)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}