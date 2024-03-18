package com.hightech.cryptoapp

import app.cash.turbine.test
import com.hightech.cryptoapp.avenger.domain.Avenger
import com.hightech.cryptoapp.avenger.domain.AvengerResult
import com.hightech.cryptoapp.avenger.domain.LoadAvengerUseCase
import com.hightech.cryptoapp.avenger.domain.Unexpected
import com.hightech.cryptoapp.avenger.presentation.AvengerItemViewModel
import com.hightech.cryptoapp.avenger.presentation.AvengerViewModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AvengerViewModelTest {
    private val useCase = spyk<LoadAvengerUseCase>()
    private lateinit var sut: AvengerViewModel

    private val avengers = listOf(
        AvengerItemViewModel(
            id = "1",
            name = "Iron Man",
            rating = "9.5",
            image = 0
        ),
        AvengerItemViewModel(
            id = "2",
            name = "Captain America",
            rating = "9.0",
            image = 1
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        sut = AvengerViewModel(useCase = useCase)

        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testInitInitialState() {
        val uiState = sut.uiState.value

        Assert.assertFalse(uiState.isLoading)
        Assert.assertTrue(uiState.avengers.isEmpty())
        assert(uiState.failed.isEmpty())
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            useCase.load()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadRequestsData() = runBlocking {
        every {
            useCase.load()
        } returns flowOf()

        sut.loadAvengers()

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking {
        every {
            useCase.load()
        } returns flowOf()

        sut.loadAvengers()
        sut.loadAvengers()

        verify(exactly = 2) {
            useCase.load()
        }

        confirmVerified(useCase)
    }


    @Test
    fun testLoadIsLoadingState() = runBlocking {
        every {
            useCase.load()
        } returns flowOf()

        sut.loadAvengers()

        sut.uiState.take(1).test {
            val receivedResult = awaitItem()
            assertEquals(true, receivedResult.isLoading)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadUnexpectedShowsUnexpectedError() {
        expect(
            sut = sut,
            expectedLoadingResult = false,
            expectedFailedResult = "Unexpected Error",
            action = {
                every {
                    useCase.load()
                } returns flowOf(AvengerResult.Failure(Unexpected()))
            }
        )
    }

    @Test
    fun testLoadSuccessShowsContacts() {
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
            expectedLoadingResult = false,
            expectedFailedResult = "",
            action = {
                every {
                    useCase.load()
                } returns flowOf(AvengerResult.Success(avengers = avengers))
            }
        )
    }

    private fun expect(
        sut: AvengerViewModel,
        expectedLoadingResult: Boolean,
        expectedFailedResult: String,
        action: () -> Unit
    ) = runBlocking {
        action()

        sut.loadAvengers()

        sut.uiState.take(1).test {
            val receivedResult = awaitItem()

            if (receivedResult.failed.isEmpty()) {
                assertEquals(expectedLoadingResult, receivedResult.isLoading)
                assertEquals(avengers, receivedResult.avengers)
                assertEquals(expectedFailedResult, receivedResult.failed)
            } else {
                assertEquals(expectedLoadingResult, receivedResult.isLoading)
                assertEquals(expectedFailedResult, receivedResult.failed)
            }
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }
}