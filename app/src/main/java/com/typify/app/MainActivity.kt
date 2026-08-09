package com.typify.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.typify.app.data.ScoringEngine
import com.typify.app.data.TestResultEntity
import com.typify.app.data.TypifyDatabase
import com.typify.app.model.Answer
import com.typify.app.model.TestResult
import com.typify.app.ui.navigation.Screen
import com.typify.app.ui.navigation.bottomNavItems
import com.typify.app.ui.screens.*
import com.typify.app.ui.theme.TypifyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TypifyTheme {
                TypifyNavHost()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypifyNavHost() {
    val navController = rememberNavController()
    val db = remember { TypifyDatabase.getInstance(navController.context) }
    val scope = rememberCoroutineScope()

    val allResults by db.testResultDao().getAll().collectAsStateWithLifecycle(initialValue = emptyList())
    var currentResult by remember { mutableStateOf<TestResult?>(null) }

    val hasOnboarded = remember {
        navController.context.getSharedPreferences("typify_prefs", 0)
            .getBoolean("onboarded", false)
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Show bottom nav only on Home, History, Settings
    val showBottomNav = currentRoute in listOf("home", "history", "settings")

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar(
                    containerColor = Color(0xFF0F0F23),
                    contentColor = Color(0xFF7C3AED),
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(item.route) {
                                        popUpTo("home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF7C3AED),
                                selectedTextColor = Color(0xFF7C3AED),
                                indicatorColor = Color(0xFF7C3AED).copy(alpha = 0.12f),
                                unselectedIconColor = Color(0xFF6B6B80),
                                unselectedTextColor = Color(0xFF6B6B80)
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = if (hasOnboarded) Screen.Home.route else Screen.Onboarding.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinish = {
                        navController.context.getSharedPreferences("typify_prefs", 0)
                            .edit().putBoolean("onboarded", true).apply()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onStartTest = { testId ->
                        navController.navigate(Screen.Quiz.createRoute(testId))
                    },
                    onNavigateHistory = { navController.navigate(Screen.History.route) },
                    onNavigateSettings = { navController.navigate(Screen.Settings.route) }
                )
            }

            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("testId") { type = NavType.StringType })
            ) { backStackEntry ->
                val testId = backStackEntry.arguments?.getString("testId") ?: "mbti"
                QuizScreen(
                    testId = testId,
                    onBack = { navController.popBackStack() },
                    onComplete = { answers ->
                        val result = ScoringEngine.calculateMBTI(answers)
                        currentResult = result

                        scope.launch {
                            db.testResultDao().insert(
                                TestResultEntity(
                                    testType = result.testType,
                                    resultType = result.typeCode,
                                    resultTitle = result.title,
                                    resultDescription = result.description,
                                    questionsAnswered = answers.size,
                                    confidenceScore = result.confidenceScore / 100f
                                )
                            )
                        }

                        navController.navigate(Screen.Result.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }

            composable(Screen.Result.route) {
                val result = currentResult
                if (result != null) {
                    ResultScreen(
                        result = result,
                        onShare = {
                            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    android.content.Intent.EXTRA_TEXT,
                                    "I'm ${result.typeCode} — ${result.title} ${result.emoji}\n\n${result.description}\n\nFind your type with Typify"
                                )
                            }
                            navController.context.startActivity(
                                android.content.Intent.createChooser(shareIntent, "Share")
                            )
                        },
                        onRetake = {
                            navController.navigate(Screen.Quiz.createRoute("mbti")) {
                                popUpTo(Screen.Home.route)
                            }
                        },
                        onHome = { navController.popBackStack() }
                    )
                }
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    results = allResults,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
