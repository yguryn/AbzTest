package com.abz.abztest.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abz.abztest.ui.MainViewModel
import com.abz.abztest.ui.theme.Black60
import com.abz.abztest.ui.theme.BottomBarBackground
import com.abz.abztest.ui.theme.IconColor
import com.abz.abztest.ui.theme.Secondary
import com.abz.abztest.ui.theme.Typography

@Composable
fun AppNavigation(
    navController: NavHostController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = remember {
        mutableStateOf(true)
    }
    when (navBackStackEntry?.destination?.route) {
        Screens.Users.route -> showBottomBar.value = true
        Screens.SignUp.route -> showBottomBar.value = true
        else -> showBottomBar.value = false
    }


    Scaffold(
        bottomBar = {
            if (showBottomBar.value) {
                AppBottomGraph(
                    navController = navController,
                )
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun AppBottomGraph(navController: NavController) {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem.Users,
            BottomNavItem.SignUp,
        )
    }

    NavigationBar(containerColor = BottomBarBackground) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Row(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavigationItems.forEach { screen ->
                AddBottomItem(
                    bottomNavItem = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun AddBottomItem(
    bottomNavItem: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable(onClick = {
                navController.navigate(bottomNavItem.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = bottomNavItem.icon),
                tint = if (selected) Secondary else IconColor,
                contentDescription = "icon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = bottomNavItem.name,
                style = Typography.bodyLarge,
                color = if (selected) Secondary else Black60
            )
        }
    }
}
