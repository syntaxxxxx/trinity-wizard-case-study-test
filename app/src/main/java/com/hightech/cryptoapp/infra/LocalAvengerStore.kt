package com.hightech.cryptoapp.infra

import com.hightech.cryptoapp.local.AvengerStore
import com.hightech.cryptoapp.local.LocalAvenger
import com.hightech.cryptoapp.local.LocalResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class LocalAvengerEntity(
    val id: String,
    val name: String,
    val rating: String,
    val image: Int
)

val localAvengers = listOf(
    LocalAvengerEntity(
        id = "1",
        name = "Iron Man",
        rating = "9.5",
        image = 0
    ),
    LocalAvengerEntity(
        id = "2",
        name = "Captain America",
        rating = "9.0",
        image = 1
    )
)

class LocalAvengerStore : AvengerStore {
    override fun get(): Flow<LocalResult> = flow {
        try {
            val data = localAvengers.toModels()
            emit(LocalResult.Success(data = data))
        } catch (e: Exception) {
            emit(LocalResult.Failure(e))
        }
    }
}

private fun List<LocalAvengerEntity>.toModels(): List<LocalAvenger> {
    return map {
        LocalAvenger(
            id = it.id,
            name = it.id,
            rating = it.id,
            image = it.image
        )
    }
}
