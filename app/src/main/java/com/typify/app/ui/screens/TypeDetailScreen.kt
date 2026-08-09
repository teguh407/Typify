package com.typify.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.data.TypeDetails
import com.typify.app.model.TestResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeDetailScreen(
    result: TestResult,
    onBack: () -> Unit
) {
    val detail = TypeDetails.getDetail(result.typeCode) ?: return

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
                Text(
                    "${result.typeCode} Deep Dive",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(result.emoji, fontSize = 24.sp)
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
            // Cognitive Functions
            item {
                SectionHeader("🧠 Cognitive Functions")
                detail.cognitiveFunctions.forEach { func ->
                    CognitiveFunctionCard(func)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Famous People
            item {
                SectionHeader("🌟 Famous ${result.typeCode}s")
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    detail.famousPeople.forEach { person ->
                        ChipCard(text = person)
                    }
                }
            }

            // Compatibility
            item {
                SectionHeader("💕 Type Compatibility")
                Column {
                    Text("Best Matches", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        detail.bestMatches.forEach { ChipCard(text = it, color = Color(0xFF10B981)) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Challenging Matches", color = Color(0xFFEC4899), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        detail.worstMatches.forEach { ChipCard(text = it, color = Color(0xFFEC4899)) }
                    }
                }
            }

            // Growth Path
            item {
                SectionHeader("🌱 Growth Path")
                detail.growthPath.forEachIndexed { index, tip ->
                    GrowthCard(step = index + 1, text = tip)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Blind Spot
            item {
                SectionHeader("👁️ Your Blind Spot")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFEC4899).copy(alpha = 0.15f),
                                    Color(0xFF7C3AED).copy(alpha = 0.1f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        detail.blindSpot,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 22.sp
                    )
                }
            }

            // Daily Insights
            item {
                SectionHeader("💡 Daily Insights")
                detail.dailyInsights.forEach { insight ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Text(
                            insight,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
    )
}

@Composable
private fun CognitiveFunctionCard(func: TypeDetails.CognitiveFunction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF7C3AED).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(func.emoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "${func.name} — ${func.fullName}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                func.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ChipCard(text: String, color: Color = Color(0xFF7C3AED)) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text, fontSize = 13.sp, color = color, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun GrowthCard(step: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFF10B981).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text("$step", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

// Simple FlowRow fallback
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    content: @Composable () -> Unit
) {
    // Use basic Row wrapping — Compose 1.6+ has FlowRow but for compatibility we use Row
    Row(modifier = modifier, horizontalArrangement = horizontalArrangement) {
        content()
    }
}
