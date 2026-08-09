package com.typify.app.ui.screens

import android.os.Build
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typify.app.data.QuestionBank
import com.typify.app.model.Answer
import com.typify.app.ui.components.AnswerScale
import com.typify.app.ui.components.GlowButton
import com.typify.app.ui.components.StoryProgressBar
import com.typify.app.ui.components.SwipeableCard

@Composable
fun QuizScreen(
    testId: String,
    onBack: () -> Unit,
    onComplete: (List<Answer>) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val questions = QuestionBank.mbtiQuestions
    val currentQuestion = remember { mutableStateOf(0) }
    val answers = remember { mutableStateListOf<Answer>() }
    val selectedValue = remember { mutableStateOf<Int?>(null) }

    val totalQuestions = questions.size
    val currentIndex = currentQuestion.value

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (currentIndex > 0) {
                        currentQuestion.value--
                        selectedValue.value = answers.find { it.questionId == questions[currentIndex - 1].id }?.value
                    } else {
                        onBack()
                    }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "${currentIndex + 1} / $totalQuestions",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            // Story-style segmented progress bar
            StoryProgressBar(
                total = totalQuestions,
                current = currentIndex,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Swipeable question card
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    if (initialState < targetState) {
                        (slideInHorizontally { it } + fadeIn()) togetherWith (slideOutHorizontally { -it } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()) togetherWith (slideOutHorizontally { it } + fadeOut())
                    }
                },
                label = "question"
            ) { index ->
                val q = questions[index]
                SwipeableCard(
                    onSwipeLeft = {
                        // Swipe left = disagree (-2)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        selectedValue.value = -2
                    },
                    onSwipeRight = {
                        // Swipe right = agree (+2)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        selectedValue.value = 2
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(28.dp)
                    ) {
                        Text(
                            text = q.text,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        // Answer scale
                        val dim = q.dimension
                        AnswerScale(
                            selectedValue = selectedValue.value,
                            onAnswer = { value ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedValue.value = value
                            },
                            leftLabel = dim.leftPole,
                            rightLabel = dim.rightPole
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Dimension label
                        Text(
                            text = "${dim.leftPole} ↔ ${dim.rightPole}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next button
            GlowButton(
                text = if (currentIndex == totalQuestions - 1) "See Results ✨" else "Next →",
                onClick = {
                    val value = selectedValue.value ?: return@GlowButton
                    val question = questions[currentIndex]
                    val existing = answers.indexOfFirst { it.questionId == question.id }
                    if (existing >= 0) {
                        answers[existing] = Answer(question.id, value)
                    } else {
                        answers.add(Answer(question.id, value))
                    }

                    if (currentIndex < totalQuestions - 1) {
                        currentQuestion.value++
                        selectedValue.value = answers.find { it.questionId == questions[currentIndex + 1].id }?.value
                    } else {
                        onComplete(answers.toList())
                    }
                },
                enabled = selectedValue.value != null,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
