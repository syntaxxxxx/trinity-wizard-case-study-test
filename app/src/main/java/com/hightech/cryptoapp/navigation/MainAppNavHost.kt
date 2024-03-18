package com.hightech.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hightech.cryptoapp.avenger.detail.cryptoDetailScreen
import com.hightech.cryptoapp.avenger.detail.navigateToCryptoDetails
import com.hightech.cryptoapp.avenger.ui.navigation.cryptoGraph
import com.hightech.cryptoapp.avenger.ui.navigation.avengerGraphRoute

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = avengerGraphRoute
) {
    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        cryptoGraph(
            onCryptoClick = navHostController::navigateToCryptoDetails
        ) {
            cryptoDetailScreen(
                navHostController = navHostController
            )
        }
    }
}