package com.typify.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import com.typify.app.model.TestResult
import com.typify.app.ui.components.GlowButton
import com.typify.app.ui.components.TypifyChip
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(
    result: TestResult,
    onShare: () -> Unit,
    onRetake: () -> Unit,
    onHome: () -> Unit
) {
    // Reveal animation states
    var revealPhase by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(100)
        revealPhase = 1  // Card pop
        delay(400)
        revealPhase = 2  // Confetti
        delay(800)
        revealPhase = 3  // Content visible
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Result card with dramatic reveal
                item {
                    ResultCard(result = result, revealPhase = revealPhase)
                }

                // Dimension breakdown (fade in after reveal)
                if (revealPhase >= 2) {
                    item {
                        val alpha by animateFloatAsState(
                            targetValue = if (revealPhase >= 2) 1f else 0f,
                            animationSpec = tween(600),
                            label = "breakdown_alpha"
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                                .graphicsLayerAlpha(alpha)
                        ) {
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
                }

                // Strengths
                if (revealPhase >= 3) {
                    item {
                        TraitSection(
                            title = "Strengths",
                            items = result.strengths,
                            color = Color(0xFF10B981),
                            icon = "💪"
                        )
                    }

                    // Growth Areas
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

            // Confetti overlay
            if (revealPhase == 2) {
                ConfettiBurst(
                    modifier = Modifier.fillMaxSize(),
                    onFinished = { revealPhase = 3 }
                )
            }
        }
    }
}

@Composable
private fun ResultCard(result: TestResult, revealPhase: Int) {
    val scale by animateFloatAsState(
        targetValue = if (revealPhase >= 1) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pop"
    )

    val alpha by animateFloatAsState(
        targetValue = if (revealPhase >= 1) 1f else 0f,
        animationSpec = tween(300),
        label = "card_alpha"
    )

    Box(
        modifier = Modifier
            .padding(20.dp)
            .scale(scale)
            .graphicsLayerAlpha(alpha)
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
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(
                text = result.title,
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold
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

// ── Confetti burst ─────────────────────────────────────────────
@Composable
private fun ConfettiBurst(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    val colors = listOf(
        Color(0xFF7C3AED), Color(0xFFEC4899), Color(0xFF06B6D4),
        Color(0xFF10B981), Color(0xFFF59E0B), Color(0xFFEF4444)
    )
    val particleCount = 40

    LaunchedEffect(Unit) {
        delay(1200)
        onFinished()
    }

    Box(modifier = modifier) {
        repeat(particleCount) { i ->
            var progress by remember { mutableStateOf(0f) }
            LaunchedEffect(i) {
                delay(i * 30L)
                progress = 1f
            }
            val animProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(1200, easing = FastOutSlowInEasing),
                label = "particle_$i"
            )

            val startX = ((i % 8) / 8f - 0.5f) * 0.6f + 0.5f
            val startY = 0.3f
            val endX = startX + ((i % 3) - 1) * 0.15f
            val endY = startY + 0.7f + (i % 5) * 0.05f
            val rotation = animProgress * 720f
            val alpha = 1f - animProgress

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            ((startX + (endX - startX) * animProgress) * 800).toInt().dp.value.toInt(),
                            ((startY + (endY - startY) * animProgress) * 1600).toInt().dp.value.toInt()
                        )
                    }
                    .size(10.dp)
                    .graphicsLayer {
                        this.rotationZ = rotation
                        this.alpha = alpha
                    }
                    .background(colors[i % colors.size])
            )
        }
    }
}

// ── Graphics layer alpha helper ────────────────────────────────
@Composable
private fun Modifier.graphicsLayerAlpha(alpha: Float): Modifier {
    return this.then(
        Modifier.graphicsLayer { this.alpha = alpha }
    )
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
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text("${confidence.toInt()}%", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
