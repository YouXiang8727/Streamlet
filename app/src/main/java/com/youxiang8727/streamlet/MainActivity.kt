package com.youxiang8727.streamlet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.youxiang8727.streamlet.domain.usecase.InitCategoriesUseCase
import com.youxiang8727.streamlet.ui.navigation.AppNavigation
import com.youxiang8727.streamlet.ui.theme.StreamletTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var initCategoriesUseCase: InitCategoriesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initCategories()

        setContent {
            StreamletTheme {
                val snackBarHostState = remember {
                    SnackbarHostState()
                }

                val snackBarScope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackBarHostState
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        snackBarHostState = snackBarHostState,
                        snackBarScope = snackBarScope
                    )
                }
            }
        }
    }

    private fun initCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            initCategoriesUseCase()
        }
    }
}
