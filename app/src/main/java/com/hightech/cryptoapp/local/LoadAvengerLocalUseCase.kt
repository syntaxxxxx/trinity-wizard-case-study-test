package com.hightech.cryptoapp.local

import com.hightech.cryptoapp.domain.Avenger
import com.hightech.cryptoapp.domain.AvengerResult
import com.hightech.cryptoapp.domain.Unexpected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadAvengerLocalUseCase(private val store: AvengerStore) {
    fun load(): Flow<AvengerResult> = flow {
        store.get().collect { result ->
            when (result) {
                is LocalResult.Success -> {
                    val avengers = result.data.toModels()
                    emit(AvengerResult.Success(avengers = avengers))
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