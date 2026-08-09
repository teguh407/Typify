package com.typify.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
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

@Composable
fun TypifyNavHost() {
    val navController = rememberNavController()
    val db = remember { TypifyDatabase.getInstance(navController.context) }
    val scope = rememberCoroutineScope()

    val allResults by db.testResultDao().getAll().collectAsStateWithLifecycle(initialValue = emptyList())

    var currentResult by remember { mutableStateOf<TestResult?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
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

                    // Save to DB
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
