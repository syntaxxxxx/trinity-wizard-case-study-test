package com.hightech.cryptoapp

import app.cash.turbine.test
import com.hightech.cryptoapp.domain.AvengerResult
import com.hightech.cryptoapp.domain.LoadAvengerUseCase
import com.hightech.cryptoapp.domain.Unexpected
import com.hightech.cryptoapp.presentation.AvengerViewModel
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
import org.junit.Before
import org.junit.Test

class AvengerViewModelTest {
    private val useCase = spyk<LoadAvengerUseCase>()
    private lateinit var sut: AvengerViewModel

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
        Assert.assertTrue(uiState.avenger.isEmpty())
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
            Assert.assertEquals(true, receivedResult.isLoading)
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
            Assert.assertEquals(expectedLoadingResult, receivedResult.isLoading)
            Assert.assertEquals(expectedFailedResult, receivedResult.failed)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }
}