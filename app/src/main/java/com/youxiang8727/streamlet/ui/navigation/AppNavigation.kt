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
import com.youxiang8727.streamlet.ui.screen.home.HomeScreen
import com.youxiang8727.streamlet.ui.screen.splash.SplashScreen
import com.youxiang8727.streamlet.ui.screen.transaction.TransactionScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlin.reflect.typeOf

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
                    navHostController.navigate(
                        TransactionScreenDestination(it.toEpochDay())
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
                TransactionScreen(modifier, LocalDate.ofEpochDay(destination.initialDateEpoch))
            }
        }
    }
}

@Serializable
data object SplashScreenDestination

@Serializable
data class TransactionScreenDestination(
    val initialDateEpoch: Long
)

@Serializable
data object HomeScreenDestination

