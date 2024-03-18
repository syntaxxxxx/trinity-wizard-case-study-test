package com.hightech.cryptoapp

import app.cash.turbine.test
import com.hightech.cryptoapp.infra.LocalAvengerStore
import com.hightech.cryptoapp.local.LocalAvenger
import com.hightech.cryptoapp.local.LocalResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalAvengerStoreTest {
    private lateinit var sut: LocalAvengerStore

    @Before
    fun setUp() {
        sut = LocalAvengerStore()
    }

    @Test
    fun testGetSuccessWithEmptyLocalAvengersEntity() = runBlocking {
        val localAvengers = listOf(
            LocalAvenger(
                id = "1",
                name = "Iron Man",
                rating = "9.5",
                image = 0
            ),
            LocalAvenger(
                id = "2",
                name = "Captain America",
                rating = "9.0",
                image = 1
            )
        )

        sut.get().test {
            val result = awaitItem()
            if (result is LocalResult.Success) {
                assertEquals(localAvengers, result.data)
            }
            awaitComplete()
        }
    }
}