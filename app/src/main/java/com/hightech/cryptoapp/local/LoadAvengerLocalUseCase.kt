package com.hightech.cryptoapp.local

import com.hightech.cryptoapp.domain.Avenger
import com.hightech.cryptoapp.domain.AvengerResult
import com.hightech.cryptoapp.domain.Unexpected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class LocalAvenger(
    val id: String,
    val name: String,
    val rating: String,
    val image: Int
)

sealed class LocalResult {
    data class Success(val data: List<LocalAvenger>) : LocalResult()
    data class Failure(val exception: Exception) : LocalResult()
}

interface AvengerStore {
    fun get(): Flow<LocalResult>
}

class LoadAvengerLocalUseCase(private val store: AvengerStore) {
    fun load(): Flow<AvengerResult> = flow {
        store.get().collect { result ->
            when (result) {
                is LocalResult.Success -> {
                    val contacts = result.data.toModels()
                    emit(AvengerResult.Success(avengers = contacts))
                }

                is LocalResult.Failure -> {
                    emit(AvengerResult.Failure(Unexpected()))
                }
            }
        }
    }
}

private fun List<LocalAvenger>.toModels(): List<Avenger> {
    return map {
        Avenger(
            id = it.id,
            name = it.name,
            rating = it.rating,
            image = it.image
        )
    }
}