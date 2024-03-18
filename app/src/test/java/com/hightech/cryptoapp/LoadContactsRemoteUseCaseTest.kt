package com.hightech.cryptoapp

import com.hightech.cryptoapp.local.AvengerStore
import com.hightech.cryptoapp.local.LoadAvengerLocalUseCase
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

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