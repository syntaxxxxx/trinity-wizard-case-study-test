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

        expect(
            sut = sut,
            expectedResult = Unexpected(),
            action = {
                every {
                    store.get()
                } returns flowOf(LocalResult.Failure(Exception()))
            },
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversEmptyAvengersOnSuccessWithEmptyAvengers() = runBlocking {
        val emptyLocalAvengers = emptyList<LocalAvenger>()
        val emptyAvengers = emptyList<Avenger>()

        expect(
            sut = sut,
            expectedResult = AvengerResult.Success(avengers = emptyAvengers),
            action = {
                every {
                    store.get()
                } returns flowOf(LocalResult.Success(data = emptyLocalAvengers))
            },
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversAvengersOnSuccessWithAvengers() = runBlocking {
        val localAvenger = listOf(
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

        val avengers = listOf(
            Avenger(
                id = "1",
                name = "Iron Man",
                rating = "9.5",
                image = 0
            ),
            Avenger(
                id = "2",
                name = "Captain America",
                rating = "9.0",
                image = 1
            )
        )

        expect(
            sut = sut,
            expectedResult = AvengerResult.Success(avengers = avengers),
            action = {
                every {
                    store.get()
                } returns flowOf(LocalResult.Success(data = localAvenger))
            },
            exactly = 1
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun expect(
        sut: LoadAvengerLocalUseCase,
        expectedResult: Any,
        action: () -> Unit,
        exactly: Int = -1,
    ) = runBlocking {
        action()

        sut.load().test {
            when (val receivedResult = awaitItem()) {
                is AvengerResult.Success -> {
                    assertEquals(
                        expectedResult,
                        receivedResult
                    )
                }
                is AvengerResult.Failure -> {
                    assertEquals(
                        expectedResult::class.java,
                        receivedResult.exception::class.java
                    )
                }
            }
            awaitComplete()
        }

        verify(exactly = exactly) {
            store.get()
        }

        confirmVerified(store)
    }
}