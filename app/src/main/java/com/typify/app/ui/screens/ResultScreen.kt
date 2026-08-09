package com.typify.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.model.TestResult
import com.typify.app.ui.components.GlowButton
import com.typify.app.ui.components.TypifyChip

@Composable
fun ResultScreen(
    result: TestResult,
    onShare: () -> Unit,
    onRetake: () -> Unit,
    onHome: () -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Result card
            item {
                ResultCard(result = result)
            }

            // Dimension breakdown
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text(
                        "Your Breakdown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    result.dimensionBreakdown.forEach { (dim, confidence) ->
                        DimensionBar(dim, confidence)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // Strengths
            item {
                TraitSection(
                    title = "Strengths",
                    items = result.strengths,
                    color = Color(0xFF10B981),
                    icon = "💪"
                )
            }

            // Weaknesses
            item {
                TraitSection(
                    title = "Growth Areas",
                    items = result.weaknesses,
                    color = Color(0xFFEC4899),
                    icon = "🌱"
                )
            }

            // Career fits
            item {
                TraitSection(
                    title = "Career Fits",
                    items = result.careerFits,
                    color = Color(0xFF7C3AED),
                    icon = "💼"
                )
            }

            // Actions
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
                    GlowButton(
                        text = "📤 Share My Type",
                        onClick = onShare,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onRetake,
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("🔄 Retake Test")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TextButton(onClick = onHome, modifier = Modifier.fillMaxWidth()) {
                        Text("Back to Home")
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultCard(result: TestResult) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pop"
    )

    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(result.gradientStart),
                        Color(result.gradientEnd)
                    )
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = result.emoji,
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result.typeCode,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = result.title,
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result.description,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            TypifyChip(
                text = "${result.confidenceScore.toInt()}% match",
                color = Color.White
            )
        }
    }
}

@Composable
private fun DimensionBar(dim: String, confidence: Float) {
    val animatedWidth by animateFloatAsState(
        targetValue = confidence / 100f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "bar"
    )

    val label = when(dim) {
        "EI" -> "E ↔ I"
        "SN" -> "S ↔ N"
        "TF" -> "T ↔ F"
        "JP" -> "J ↔ P"
        else -> dim
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
            Text("${confidence.toInt()}%", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedWidth)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF7C3AED), Color(0xFFEC4899))
                        )
                    )
            )
        }
    }
}

@Composable
private fun TraitSection(
    title: String,
    items: List<String>,
    color: Color,
    icon: String
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(color)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    item,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp
                )
            }
        }
    }
}
