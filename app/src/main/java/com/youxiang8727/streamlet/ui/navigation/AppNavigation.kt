package com.youxiang8727.streamlet.ui.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.ui.screen.home.HomeScreen
import com.youxiang8727.streamlet.ui.screen.splash.SplashScreen
import com.youxiang8727.streamlet.ui.screen.transaction.TransactionScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val LocalNavigationController = staticCompositionLocalOf<NavHostController> {
    error("No navigation controller provided")
}

val LocalSnackBarHostState = compositionLocalOf<SnackbarHostState> {
    error("No snack bar host state provided")
}

val LocalSnackBarScope = staticCompositionLocalOf<CoroutineScope> {
    error("No snack bar scope provided")
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    snackBarScope: CoroutineScope
) {
    val navHostController = rememberNavController()
    CompositionLocalProvider(
        LocalNavigationController provides navHostController,
        LocalSnackBarHostState provides snackBarHostState,
        LocalSnackBarScope provides snackBarScope
    ) {
        NavHost(
            navController = navHostController,
            startDestination = SplashScreenDestination
        ) {
            composable<SplashScreenDestination>(
                exitTransition = {
                    scaleOut(
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                }
            ) {
                SplashScreen(modifier) {
                    navHostController.navigate(HomeScreenDestination) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }

            composable<HomeScreenDestination> {
                HomeScreen(modifier) {
                    val transactionDataJsonString = if (it == null) {
                        null
                    } else {
                        Json.encodeToString(it)
                    }
                    navHostController.navigate(
                        TransactionScreenDestination(transactionDataJsonString)
                    )
                }
            }

            composable<TransactionScreenDestination>(
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(300)
                    )
                },
            ) {
                val destination: TransactionScreenDestination = it.toRoute()
                val transactionData = if (destination.transactionDataJsonString == null) {
                    null
                } else {
                    Json.decodeFromString<TransactionData>(destination.transactionDataJsonString)
                }
                TransactionScreen(modifier, transactionData)
            }
        }
    }
}

@Serializable
data object SplashScreenDestination

@Serializable
data class TransactionScreenDestination(
    val transactionDataJsonString: String? = null
)

@Serializable
data object HomeScreenDestination

