package com.hightech.cryptoapp.local

import kotlinx.coroutines.flow.Flow

sealed class LocalResult {
    data class Success(val data: List<LocalAvenger>) : LocalResult()
    data class Failure(val exception: Exception) : LocalResult()
}

interface AvengerStore {
    fun get(): Flow<LocalResult>
}