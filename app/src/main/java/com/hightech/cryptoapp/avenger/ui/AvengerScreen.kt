package com.hightech.cryptoapp.avenger.ui

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hightech.cryptoapp.FACTORY
import com.hightech.cryptoapp.avenger.presentation.AvengerItemViewModel
import com.hightech.cryptoapp.avenger.presentation.AvengerViewModel
import com.hightech.cryptoapp.avenger.presentation.UiState
import com.hightech.cryptoapp.avenger.ui.components.AvengerList
import com.hightech.cryptoapp.theme.Purple40
import kotlinx.parcelize.Parcelize


@Parcelize
data class AvengerItemUi(
    val id: String,
    val name: String,
    val rating: String,
    val image: Int
): Parcelable

@Composable
fun AvengerRoute(
    viewModel: AvengerViewModel = viewModel(factory = FACTORY),
    onNavigateToCryptoDetails: (AvengerItemUi) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CryptoFeedScreen(
        cryptoFeedUiState = uiState,
        onRefreshCryptoFeed = viewModel::loadAvengers,
        onNavigateToCryptoDetails = onNavigateToCryptoDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CryptoFeedScreen(
    modifier: Modifier = Modifier,
    cryptoFeedUiState: UiState,
    onRefreshCryptoFeed: () -> Unit,
    onNavigateToCryptoDetails: (AvengerItemUi) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = cryptoFeedUiState.isLoading,
        onRefresh = onRefreshCryptoFeed
    )

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Avengers",
                    maxLines = 1,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Purple40
            )
        )
    }, content = {
        val contentModifier = modifier
            .padding(it)
            .fillMaxSize()
            .pullRefresh(pullRefreshState)

        LoadingContent(
            pullRefreshState = pullRefreshState,
            loading = cryptoFeedUiState.isLoading,
            empty = if (cryptoFeedUiState.avengers.isNotEmpty()) false else cryptoFeedUiState.isLoading,
            emptyContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    PullRefresh(
                        loading = cryptoFeedUiState.isLoading,
                        pullRefreshState = pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }
            },
            content = {
                if (cryptoFeedUiState.failed.isEmpty() && cryptoFeedUiState.avengers.isEmpty()) {
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            "Avengers Empty",
                        )
                    }
                } else if (cryptoFeedUiState.failed.isNotEmpty()) {
                    Box(
                        modifier = modifier
                            .padding(it)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            cryptoFeedUiState.failed,
                        )
                    }
                } else {
                    AvengerList(
                        contentModifier = contentModifier,
                        items = cryptoFeedUiState.avengers.toModels(),
                        onNavigateToCryptoDetails = onNavigateToCryptoDetails
                    )
                }
            })
    })
}

private fun List<AvengerItemViewModel>.toModels(): List<AvengerItemUi> {
    return map {
        AvengerItemUi(
            id = it.id,
            name = it.name,
            rating = it.rating,
            image = it.image,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingContent(
    loading: Boolean,
    pullRefreshState: PullRefreshState,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    if (empty) {
        emptyContent()
    } else {
        Box(
            modifier = Modifier, contentAlignment = Alignment.Center
        ) {
            content()

            PullRefresh(
                loading = loading,
                pullRefreshState = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefresh(
    loading: Boolean,
    pullRefreshState: PullRefreshState,
    modifier: Modifier
) {
    PullRefreshIndicator(
        refreshing = loading,
        state = pullRefreshState,
        modifier = modifier
    )
}