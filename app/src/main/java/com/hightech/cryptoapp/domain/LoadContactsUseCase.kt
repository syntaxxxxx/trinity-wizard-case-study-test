package com.hightech.cryptoapp.domain

import kotlinx.coroutines.flow.Flow
import kotlin.Exception

class Unexpected: Exception()

sealed class ContactResult {
    data class Success(val contacts: List<Avenger>) : ContactResult()
    data class Failure(val exception: Exception) : ContactResult()
}

interface LoadContactsUseCase {
    fun load(): Flow<ContactResult>
}