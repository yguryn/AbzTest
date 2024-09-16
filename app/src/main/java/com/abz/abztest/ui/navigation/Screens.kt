package com.abz.abztest.ui.navigation

sealed class Screens(val route: String) {
    data object Users: Screens("users")
    data object SignUp: Screens("signUp")
    data object NoInternet: Screens("noInternet")
    data object SignUpSuccess: Screens("signUpSuccess")
    data object SignUpFail: Screens("signUpFail")
}