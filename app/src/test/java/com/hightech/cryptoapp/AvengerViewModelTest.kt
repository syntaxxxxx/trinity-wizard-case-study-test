package com.hightech.cryptoapp

import com.hightech.cryptoapp.domain.LoadAvengerUseCase
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

data class UiState(
    val isLoading: Boolean = false,
    val avenger: List<AvengerItemViewModel> = emptyList(),
    val failed: String = "",
)

data class AvengerItemViewModel(
    val id: String,
    val name: String,
    val rating: String,
    val image: Int
)

class ContactsViewModel {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}

class ContactsViewModelTest {
    private val useCase = spyk<LoadAvengerUseCase>()
    private lateinit var sut: ContactsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        sut = ContactsViewModel()

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
}