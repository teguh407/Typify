package com.typify.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.data.GamificationManager
import com.typify.app.data.TestResultEntity
import com.typify.app.data.TypeDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    results: List<TestResultEntity>,
    onBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val gamification = remember { GamificationManager(context) }
    val stats = remember { gamification.getStats() }
    val dateFmt = remember { SimpleDateFormat("MMM d, yyyy", Locale.US) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("History", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        if (results.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("📭", fontSize = 40.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("No tests yet", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Take your first test!", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Analytics section
            item {
                Text("Analytics", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(top = 12.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Consistency score
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF7C3AED).copy(alpha = 0.15f),
                                    Color(0xFFEC4899).copy(alpha = 0.1f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Type Consistency", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                            Text("${stats.consistencyScore}% consistent", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        // Visual consistency ring
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF7C3AED).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${stats.consistencyScore}%", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color(0xFF7C3AED))
                        }
                    }
                }
            }

            // Stats row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBox("Tests", stats.totalTests.toString(), Modifier.weight(1f))
                    StatBox("Types Found", stats.typesDiscovered.size.toString(), Modifier.weight(1f))
                    StatBox("Best Streak", stats.longestStreak.toString(), Modifier.weight(1f))
                }
            }

            // Type distribution
            if (stats.typesDiscovered.isNotEmpty()) {
                item {
                    Text("Types Discovered", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(top = 8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        stats.typesDiscovered.forEach { type ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF7C3AED).copy(alpha = 0.12f))
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(type, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF7C3AED))
                            }
                        }
                    }
                }
            }

            // Badges
            if (stats.badges.any { it.unlocked }) {
                item {
                    Text("Badges (${stats.badges.count { it.unlocked }}/${stats.badges.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(top = 8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        stats.badges.forEach { badge ->
                            BadgeMini(badge)
                        }
                    }
                }
            }

            // Test history
            item {
                Text("Test History", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(top = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Timeline items
            items(results.reversed()) { result ->
                HistoryItem(result = result, dateFmt = dateFmt)
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun BadgeMini(badge: GamificationManager.Badge) {
    val alpha = if (badge.unlocked) 1f else 0.3f
    Column(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (badge.unlocked) Color(0xFF7C3AED).copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(badge.emoji, fontSize = 20.sp, modifier = Modifier.alpha(alpha))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(badge.name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
    }
}

@Composable
private fun HistoryItem(result: TestResultEntity, dateFmt: SimpleDateFormat) {
    val detail = TypeDetails.getDetail(result.resultType)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(result.resultType, fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFF7C3AED))
                Text(result.resultTitle, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("${result.questionsAnswered} Q · ${(result.confidenceScore * 100).toInt()}% match", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(dateFmt.format(Date(result.takenAt)), fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                detail?.let { Text(it.famousPeople.firstOrNull() ?: "", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline) }
            }
        }
    }
}

