package com.abz.abztest.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abz.abztest.ui.NoInternetScreen
import com.abz.abztest.ui.signup.EmailAlreadyRegisteredScreen
import com.abz.abztest.ui.signup.SignUpScreen
import com.abz.abztest.ui.signup.UserSuccessfullyRegisteredScreen
import com.abz.abztest.ui.users.UsersScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Users.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Screens.Users.route) {
            UsersScreen()
        }
        composable(route = Screens.SignUp.route) {
            SignUpScreen(
                signUpSuccess = { navController.navigate(Screens.SignUpSuccess.route) },
                signUpFail = {
                    navController.navigate(Screens.SignUpFail.route)
                })
        }
        composable(route = Screens.NoInternet.route) {
            BackHandler(true) {}
            NoInternetScreen()
        }
        composable(route = Screens.SignUpSuccess.route) {
            UserSuccessfullyRegisteredScreen {
                navController.navigate(Screens.Users.route)
            }
        }
        composable(route = Screens.SignUpFail.route) {
            EmailAlreadyRegisteredScreen() {
                navController.popBackStack()
            }
        }
    }
}