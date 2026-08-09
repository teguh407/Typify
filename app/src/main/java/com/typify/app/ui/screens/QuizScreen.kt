package com.typify.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import com.typify.app.data.QuestionBank
import com.typify.app.model.Answer
import com.typify.app.model.Dimension
import com.typify.app.ui.components.AnswerScale
import com.typify.app.ui.components.AnimatedProgressBar
import com.typify.app.ui.components.GlowButton

@Composable
fun QuizScreen(
    testId: String,
    onBack: () -> Unit,
    onComplete: (List<Answer>) -> Unit
) {
    val questions = QuestionBank.mbtiQuestions
    val currentQuestion = remember { mutableStateOf(0) }
    val answers = remember { mutableStateListOf<Answer>() }
    val selectedValue = remember { mutableStateOf<Int?>(null) }

    val totalQuestions = questions.size
    val currentIndex = currentQuestion.value
    val question = questions[currentIndex]

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
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
            // Progress bar
            AnimatedProgressBar(
                progress = (currentIndex + 1f) / totalQuestions,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Question text
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
                Column {
                    Text(
                        text = q.text,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // Answer scale
                    val dim = q.dimension
                    AnswerScale(
                        selectedValue = selectedValue.value,
                        onAnswer = { value ->
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

            Spacer(modifier = Modifier.weight(1f))

            // Next button
            GlowButton(
                text = if (currentIndex == totalQuestions - 1) "See Results" else "Next",
                onClick = {
                    val value = selectedValue.value ?: return@GlowButton
                    // Update or add answer
                    val existing = answers.indexOfFirst { it.questionId == question.id }
                    if (existing >= 0) {
                        answers[existing] = Answer(question.id, value)
                    } else {
                        answers.add(Answer(question.id, value))
                    }

                    if (currentIndex < totalQuestions - 1) {
                        currentQuestion.value++
                        selectedValue.value =
                            answers.find { it.questionId == questions[currentIndex + 1].id }?.value
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
