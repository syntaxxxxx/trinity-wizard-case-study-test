package com.hightech.cryptoapp.avenger.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hightech.cryptoapp.avenger.ui.AvengerItemUi
import com.hightech.cryptoapp.avenger.ui.AvengerRoute

const val avengerGraphRoute = "avenger_graph_route"
const val avengerRoute = "avenger_route"

fun NavGraphBuilder.cryptoGraph(
    onCryptoClick: (AvengerItemUi) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = avengerGraphRoute,
        startDestination = avengerRoute
    ) {
        composable(
            route = avengerRoute
        ) {
            AvengerRoute(onNavigateToCryptoDetails = onCryptoClick)
        }
        nestedGraphs()
    }
}