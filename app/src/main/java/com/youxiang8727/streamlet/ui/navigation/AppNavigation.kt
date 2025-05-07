package com.youxiang8727.streamlet.ui.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.youxiang8727.streamlet.ui.screen.splash.SplashScreen
import com.youxiang8727.streamlet.ui.screen.transaction.TransactionScreen
import kotlinx.serialization.Serializable

val LocalNavigationController = staticCompositionLocalOf<NavHostController> {
    error("No navigation controller provided")
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberNavController()
    CompositionLocalProvider(LocalNavigationController provides navHostController) {
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
                    navHostController.navigate(TransactionScreenDestination) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }

            composable<TransactionScreenDestination> {
                TransactionScreen(modifier)
            }
        }
    }
}

@Serializable
data object SplashScreenDestination

@Serializable
data object TransactionScreenDestination