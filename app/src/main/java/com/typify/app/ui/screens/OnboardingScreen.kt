package com.typify.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.ui.components.GlowButton

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val currentPage = remember { mutableStateOf(0) }
    val pages = listOf(
        OnboardPage("🧠", "Discover Your\nPersonality", "Take beautifully designed tests to find out who you really are."),
        OnboardPage("⏱️", "60 Questions\n5 Minutes", "Answer honestly. There are no wrong answers — just be yourself."),
        OnboardPage("🔒", "100% Private", "No data collection. No tracking. Everything stays on your device. Always.")
    )

    val page = pages[currentPage.value]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Emoji
        Text(
            text = page.emoji,
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            text = page.desc,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Dots indicator
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage.value)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                )
            }
        }

        // Skip button
        TextButton(
            onClick = onFinish,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Skip", color = MaterialTheme.colorScheme.outline, fontSize = 13.sp)
        }

        // Action button
        GlowButton(
            text = if (currentPage.value == 2) "Start Testing" else "Next",
            onClick = {
                if (currentPage.value < 2) {
                    currentPage.value++
                } else {
                    onFinish()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private data class OnboardPage(
    val emoji: String,
    val title: String,
    val desc: String
)
