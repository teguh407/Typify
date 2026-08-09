package com.typify.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun GradientCard(
    gradientStart: Long,
    gradientEnd: Long,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(gradientStart),
                        Color(gradientEnd)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.padding(24.dp), content = content)
    }
}

@Composable
fun AnswerScale(
    selectedValue: Int?,
    onAnswer: (Int) -> Unit,
    leftLabel: String,
    rightLabel: String
) {
    val options = listOf(-2, -1, 0, 1, 2)
    val labels = listOf("Strongly\nDisagree", "Disagree", "Neutral", "Agree", "Strongly\nAgree")
    val colors = listOf(
        Color(0xFFE11D48), // strong disagree
        Color(0xFFEC4899), // disagree
        Color(0xFF6B6B80), // neutral
        Color(0xFF9F67F0), // agree
        Color(0xFF7C3AED)  // strong agree
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Scale circles
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, value ->
                val isSelected = selectedValue == value
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.15f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "scale"
                )

                Box(
                    modifier = Modifier
                        .size((48 * scale).dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) colors[index]
                            else colors[index].copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Text("✓", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    gradientStart: Long = 0xFF7C3AED,
    gradientEnd: Long = 0xFFEC4899
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Box(
        modifier = modifier
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(gradientStart), Color(gradientEnd))
                    )
                )
        )
    }
}

@Composable
fun TypifyChip(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradientStart: Long = 0xFF7C3AED,
    gradientEnd: Long = 0xFFEC4899
) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.4f,
        label = "glow"
    )

    Box(
        modifier = modifier
            .height(54.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(gradientStart).copy(alpha = alpha),
                        Color(gradientEnd).copy(alpha = alpha)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}
