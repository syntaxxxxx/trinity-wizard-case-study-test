package com.hightech.cryptoapp.avenger.domain

import kotlinx.coroutines.flow.Flow
import kotlin.Exception

class Unexpected: Exception()

sealed class AvengerResult {
    data class Success(val avengers: List<Avenger>) : AvengerResult()
    data class Failure(val exception: Exception) : AvengerResult()
}

interface LoadAvengerUseCase {
    fun load(): Flow<AvengerResult>
}