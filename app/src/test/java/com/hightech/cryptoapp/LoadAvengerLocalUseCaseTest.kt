package com.hightech.cryptoapp

import app.cash.turbine.test
import com.hightech.cryptoapp.domain.Avenger
import com.hightech.cryptoapp.domain.AvengerResult
import com.hightech.cryptoapp.domain.Unexpected
import com.hightech.cryptoapp.local.AvengerStore
import com.hightech.cryptoapp.local.LoadAvengerLocalUseCase
import com.hightech.cryptoapp.local.LocalAvenger
import com.hightech.cryptoapp.local.LocalResult
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
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


    @Test
    fun testLoadRequestsData() = runBlocking {
        every {
            store.get()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }

        verify(exactly = 1) {
            store.get()
        }

        confirmVerified(store)
    }

    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking {
        every {
            store.get()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }

        sut.load().test {
            awaitComplete()
        }

        verify(exactly = 2) {
            store.get()
        }

        confirmVerified(store)
    }

    @Test
    fun testLoadDeliversUnexpectedError() = runBlocking {
        every {
            store.get()
        } returns flowOf(LocalResult.Failure(Exception()))

        sut.load().test {
            val result = awaitItem()
            if (result is AvengerResult.Failure) {
                assertEquals(Unexpected()::class.java, result.exception::class.java)
            }
            awaitComplete()
        }

        verify(exactly = 1) {
            store.get()
        }

        confirmVerified(store)
    }

    @Test
    fun testLoadDeliversEmptyAvengersOnSuccessWithEmptyAvengers() = runBlocking {
        val emptyRemoteContacts = emptyList<LocalAvenger>()
        val contacts = emptyList<Avenger>()

        every {
            store.get()
        } returns flowOf(LocalResult.Success(data = emptyRemoteContacts))

        sut.load().test {
            val result = awaitItem()
            if (result is AvengerResult.Success) {
                assertEquals(contacts, result.avengers)
            }
            awaitComplete()
        }

        verify(exactly = 1) {
            store.get()
        }

        confirmVerified(store)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}