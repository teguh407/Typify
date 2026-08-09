package com.typify.app.data

import com.typify.app.model.*
import kotlin.math.abs

object ScoringEngine {

    fun calculateMBTI(answers: List<Answer>): TestResult {
        val dimensions = mutableMapOf(
            Dimension.EI.code to 0,
            Dimension.SN.code to 0,
            Dimension.TF.code to 0,
            Dimension.JP.code to 0
        )

        val questions = QuestionBank.mbtiQuestions.associateBy { it.id }
        val perDimensionCount = mutableMapOf(*Dimension.entries.map { it.code to 0 }.toTypedArray())

        for (answer in answers) {
            val question = questions[answer.questionId] ?: continue
            val dim = question.dimension
            perDimensionCount[dim.code] = (perDimensionCount[dim.code] ?: 0) + 1

            // Positive value = agree with statement (LEFT direction = first letter: E, S, T, J)
            // Negative value = disagree (RIGHT direction = second letter: I, N, F, P)
            val contribution = if (question.direction == Direction.LEFT) {
                answer.value // agree pushes toward first letter
            } else {
                -answer.value // agree pushes toward second letter (negate)
            }
            dimensions[dim.code] = (dimensions[dim.code] ?: 0) + contribution
        }

        // Determine each letter
        val typeCode = buildString {
            append(if ((dimensions[Dimension.EI.code] ?: 0) >= 0) "E" else "I")
            append(if ((dimensions[Dimension.SN.code] ?: 0) >= 0) "S" else "N")
            append(if ((dimensions[Dimension.TF.code] ?: 0) >= 0) "T" else "F")
            append(if ((dimensions[Dimension.JP.code] ?: 0) >= 0) "J" else "P")
        }

        // Calculate per-dimension confidence (0-100%)
        val breakdown = mutableMapOf<String, Float>()
        for (dim in Dimension.entries) {
            val raw = dimensions[dim.code] ?: 0
            val count = perDimensionCount[dim.code] ?: 1
            val maxPossible = count * 2
            val confidence = if (maxPossible > 0) (abs(raw).toFloat() / maxPossible) * 100f else 0f
            breakdown[dim.code] = confidence
        }

        val avgConfidence = breakdown.values.average().toFloat()
        val personality = QuestionBank.personalityTypes[typeCode]
            ?: QuestionBank.personalityTypes["INTJ"]!! // fallback

        return TestResult(
            testType = "mbti",
            typeCode = personality.code,
            title = personality.title,
            nickname = personality.nickname,
            description = personality.description,
            strengths = personality.strengths,
            weaknesses = personality.weaknesses,
            careerFits = personality.careerFits,
            emoji = personality.emoji,
            gradientStart = personality.gradientStart,
            gradientEnd = personality.gradientEnd,
            confidenceScore = avgConfidence,
            dimensionBreakdown = breakdown
        )
    }
}
