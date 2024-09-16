package com.abz.abztest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.abz.domain.model.NetworkStatus
import com.abz.abztest.ui.navigation.AppNavigation
import com.abz.abztest.ui.navigation.Screens
import com.abz.abztest.ui.theme.AbzTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }
        enableEdgeToEdge()
        actionBar?.hide()
        setContent {
            val navController = rememberNavController()

            val networkStatus by viewModel.networkStatus.collectAsState(NetworkStatus.Available)

            if (networkStatus == NetworkStatus.Unavailable) {
                navController.navigate(Screens.NoInternet.route)
            } else {
                navController.popBackStack()
            }
            AbzTestTheme {
                AppNavigation(navController = navController)
            }
        }
    }
}