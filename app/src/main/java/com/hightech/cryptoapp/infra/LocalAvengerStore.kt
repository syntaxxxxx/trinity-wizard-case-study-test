package com.hightech.cryptoapp.infra

import com.hightech.cryptoapp.R
import com.hightech.cryptoapp.local.AvengerStore
import com.hightech.cryptoapp.local.LocalAvenger
import com.hightech.cryptoapp.local.LocalResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val localAvengers = listOf(
    LocalAvengerEntity(
        id = "1",
        name = "Superman",
        rating = "Very Good",
        image = R.drawable.superman
    ),
    LocalAvengerEntity(
        id = "2",
        name = "Ironman",
        rating = "Normal",
        image = R.drawable.avenger_ironman
    ),
    LocalAvengerEntity(
        id = "3",
        name = "Hulk",
        rating = "Awesome",
        image = R.drawable.avenger_hulk
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
            name = it.name,
            rating = it.rating,
            image = it.image
        )
    }
}
