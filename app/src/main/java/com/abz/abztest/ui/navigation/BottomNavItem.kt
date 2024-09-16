package com.abz.abztest.ui.navigation

import com.abz.common.R

sealed class BottomNavItem(
    val route: String,
    val name: String,
    val icon: Int
) {
    data object Users :
        BottomNavItem(
            Screens.Users.route,
            "Users", R.drawable.users_img,
        )

    data object SignUp :
        BottomNavItem(Screens.SignUp.route, "Sign Up", R.drawable.sign_up_img)
}

