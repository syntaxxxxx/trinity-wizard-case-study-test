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
                name = "Superman",
                rating = "Very Good",
                image = R.drawable.superman
            ),
            LocalAvenger(
                id = "2",
                name = "Ironman",
                rating = "Normal",
                image = R.drawable.avenger_ironman
            ),
            LocalAvenger(
                id = "3",
                name = "Hulk",
                rating = "Awesome",
                image = R.drawable.avenger_hulk
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