package com.typify.app.ui.components

import android.os.Build
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Gradient Card ──────────────────────────────────────────────
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
                    colors = listOf(Color(gradientStart), Color(gradientEnd))
                )
            )
    ) {
        Column(modifier = Modifier.padding(24.dp), content = content)
    }
}

// ── Answer Scale with haptic ───────────────────────────────────
@Composable
fun AnswerScale(
    selectedValue: Int?,
    onAnswer: (Int) -> Unit,
    leftLabel: String,
    rightLabel: String
) {
    val haptic = LocalHapticFeedback.current
    val options = listOf(-2, -1, 0, 1, 2)
    val labels = listOf("Strongly\nDisagree", "Disagree", "Neutral", "Agree", "Strongly\nAgree")
    val colors = listOf(
        Color(0xFFE11D48), Color(0xFFEC4899), Color(0xFF6B6B80),
        Color(0xFF9F67F0), Color(0xFF7C3AED)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, value ->
                val isSelected = selectedValue == value
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "scale"
                )

                Box(
                    modifier = Modifier
                        .size((44 * scale).dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) colors[index] else colors[index].copy(alpha = 0.12f))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onAnswer(value)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Text("✓", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ── Story-style segmented progress bar ─────────────────────────
@Composable
fun StoryProgressBar(
    total: Int,
    current: Int,
    modifier: Modifier = Modifier,
    gradientStart: Long = 0xFF7C3AED,
    gradientEnd: Long = 0xFFEC4899
) {
    Row(
        modifier = modifier.fillMaxWidth().height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        repeat(total) { index ->
            val animatedProgress by animateFloatAsState(
                targetValue = when {
                    index < current -> 1f
                    index == current -> 1f
                    else -> 0f
                },
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
                label = "segment_$index"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(gradientStart), Color(gradientEnd))
                            )
                        )
                )
            }
        }
    }
}

// ── Legacy progress bar (kept for compat) ──────────────────────
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

// ── Typify Chip ────────────────────────────────────────────────
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

// ── Glow Button with bounce + haptic ───────────────────────────
@Composable
fun GlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradientStart: Long = 0xFF7C3AED,
    gradientEnd: Long = 0xFFEC4899
) {
    val haptic = LocalHapticFeedback.current
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.35f,
        label = "glow"
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "btn_scale"
    )

    Box(
        modifier = modifier
            .height(54.dp)
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(gradientStart).copy(alpha = alpha),
                        Color(gradientEnd).copy(alpha = alpha)
                    )
                )
            )
            .clickable(enabled = enabled) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

// ── Swipeable Question Card (Tinder-style) ─────────────────────
@Composable
fun SwipeableCard(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val threshold = 200f
                        when {
                            offsetX > threshold -> onSwipeRight()
                            offsetX < -threshold -> onSwipeLeft()
                        }
                        offsetX = 0f
                        offsetY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                )
            }
            .graphicsLayer {
                translationX = offsetX
                translationY = offsetY
                rotationZ = offsetX * 0.05f
            },
        content = content
    )
}

// ── Tilt Card (micro-interaction) ──────────────────────────────
@Composable
fun TiltCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    var tiltX by remember { mutableStateOf(0f) }
    var tiltY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        tiltX = 0f
                        tiltY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        tiltX += dragAmount.x * 0.3f
                        tiltY += dragAmount.y * 0.3f
                    }
                )
            }
            .graphicsLayer {
                rotationZ = tiltX * 0.1f
                rotationX = -tiltY * 0.1f
            },
        content = content
    )
}

// ── Shimmer placeholder ────────────────────────────────────────
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF252540),
                        Color(0xFF1A1A2E)
                    ),
                    start = androidx.compose.ui.geometry.Offset(translateAnim, 0f),
                    end = androidx.compose.ui.geometry.Offset(translateAnim + 300f, 100f)
                )
            )
    )
}
