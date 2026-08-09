package com.typify.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.data.GamificationManager
import com.typify.app.data.QuestionBank
import com.typify.app.model.TestType
import com.typify.app.ui.components.GradientCard
import com.typify.app.ui.components.TypifyChip

@Composable
fun HomeScreen(
    onStartTest: (String) -> Unit,
    onNavigateHistory: () -> Unit,
    onNavigateSettings: () -> Unit
) {
    val tests = QuestionBank.testTypes
    val context = androidx.compose.ui.platform.LocalContext.current
    val gamification = remember { GamificationManager(context) }
    val stats = remember { gamification.getStats() }
    val dailyInsight = remember { gamification.getDailyInsight(stats.typesDiscovered.firstOrNull()) }
    val lastType = stats.typesDiscovered.lastOrNull()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Typify",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Black
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Streak counter
                    if (stats.currentStreak > 0) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF59E0B).copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text("🔥 ${stats.currentStreak}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    IconButton(onClick = onNavigateHistory) {
                        Icon(Icons.Default.History, contentDescription = "History")
                    }
                    IconButton(onClick = onNavigateSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Your Type card (if user has tested before)
            if (lastType != null) {
                item {
                    val detail = com.typify.app.data.TypeDetails.getDetail(lastType)
                    GradientCard(
                        gradientStart = 0xFF7C3AED,
                        gradientEnd = 0xFFEC4899,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onStartTest("mbti") }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(detail?.let { "🧠" } ?: "✨", fontSize = 32.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("You're $lastType", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                detail?.let {
                                    Text(it.famousPeople.take(2).joinToString(", "), color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                                }
                            }
                            Text("Retake →", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                        }
                    }
                }
            }

            // Daily Insight card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Daily Insight", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            dailyInsight,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }

            // Stats summary row
            if (stats.totalTests > 0) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard("Tests", stats.totalTests.toString(), "🎯", Modifier.weight(1f))
                        StatCard("Types", stats.typesDiscovered.size.toString(), "🧬", Modifier.weight(1f))
                        StatCard("Best Streak", stats.longestStreak.toString(), "🔥", Modifier.weight(1f))
                    }
                }
            }

            // Badges row
            if (stats.badges.any { it.unlocked }) {
                item {
                    Text("Badges", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(stats.badges.filter { it.unlocked }) { badge ->
                            BadgeCard(badge)
                        }
                    }
                }
            }

            // Section title
            item {
                Text(
                    "Tests",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Test cards
            items(tests) { test ->
                TestTypeCard(test = test, onClick = { onStartTest(test.id) })
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, emoji: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.Black, fontSize = 22.sp, color = MaterialTheme.colorScheme.onBackground)
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun BadgeCard(badge: GamificationManager.Badge) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF7C3AED).copy(alpha = 0.08f))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF7C3AED).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(badge.emoji, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(badge.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
        Text(badge.description, fontSize = 9.sp, color = MaterialTheme.colorScheme.outline, textAlign = TextAlign.Center)
    }
}

@Composable
private fun TestTypeCard(test: TestType, onClick: () -> Unit) {
    val alpha = if (test.available) 1f else 0.5f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(test.gradientColors[0]).copy(alpha = alpha * 0.3f),
                        Color(test.gradientColors[1]).copy(alpha = alpha * 0.2f)
                    )
                )
            )
            .clickable(enabled = test.available, onClick = onClick)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = test.icon, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(test.title, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(test.subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                if (test.available) {
                    Text("${test.questionCount} Q · 5 min", color = MaterialTheme.colorScheme.outline, fontSize = 11.sp)
                }
            }
            if (!test.available) {
                TypifyChip(text = "Soon", color = MaterialTheme.colorScheme.tertiary)
            }
        }
    }
}
