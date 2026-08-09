package com.typify.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Insights
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Quiz : Screen("quiz/{testId}") {
        fun createRoute(testId: String) = "quiz/$testId"
    }
    object Result : Screen("result")
    object History : Screen("history")
    object Settings : Screen("settings")
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Default.Home),
    BottomNavItem("history", "History", Icons.Default.Insights),
    BottomNavItem("settings", "Settings", Icons.Default.Person),
)
