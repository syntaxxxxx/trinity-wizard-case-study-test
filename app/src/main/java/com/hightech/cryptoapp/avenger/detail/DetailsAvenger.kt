package com.hightech.cryptoapp.avenger.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hightech.cryptoapp.avenger.ui.AvengerItemUi

const val avengerDetailsRoute = "avenger_details_route"

fun NavController.navigateToCryptoDetails(item: AvengerItemUi, navOptions: NavOptions? = null) {
    this.currentBackStackEntry?.savedStateHandle?.set(key = "avenger", value = item)
    this.navigate(avengerDetailsRoute, navOptions)
}

fun NavGraphBuilder.cryptoDetailScreen(
    navHostController: NavHostController
) {
    composable(
        route = avengerDetailsRoute
    ) {
        val result = navHostController.previousBackStackEntry?.savedStateHandle?.get<AvengerItemUi>("avenger")
        CryptoDetailsRoute(
            popBackStack = navHostController::popBackStack,
            item = result
        )
    }
}

@Composable
fun CryptoDetailsRoute(
    item: AvengerItemUi?,
    popBackStack: () -> Unit,
) {
    CryptoDetailsScreen(
        name = item?.name,
        popBackStack = popBackStack
    )
}

@Composable
fun CryptoDetailsScreen(
    name: String?,
    popBackStack: () -> Unit,
) {
    Text(text = name.orEmpty())
}