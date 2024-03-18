package com.hightech.cryptoapp.domain

import kotlinx.coroutines.flow.Flow
import kotlin.Exception

class Unexpected: Exception()

sealed class AvengerResult {
    data class Success(val contacts: List<Avenger>) : AvengerResult()
    data class Failure(val exception: Exception) : AvengerResult()
}

interface LoadContactsUseCase {
    fun load(): Flow<AvengerResult>
}