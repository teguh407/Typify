package com.typify.app.model

data class Question(
    val id: Int,
    val text: String,
    val dimension: Dimension,
    val direction: Direction
)

enum class Dimension(val code: String, val leftPole: String, val rightPole: String) {
    EI("EI", "Extraversion", "Introversion"),
    SN("SN", "Sensing", "Intuition"),
    TF("TF", "Thinking", "Feeling"),
    JP("JP", "Judging", "Perceiving");

    companion object {
        fun fromCode(code: String) = entries.first { it.code == code }
    }
}

enum class Direction { LEFT, RIGHT }

data class Answer(
    val questionId: Int,
    val value: Int // -2 strongly left, -1 left, 0 neutral, +1 right, +2 strongly right
)

data class TestType(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: String,
    val questionCount: Int,
    val gradientColors: List<Long>,
    val available: Boolean = true
)

data class PersonalityType(
    val code: String,
    val title: String,
    val nickname: String,
    val description: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val careerFits: List<String>,
    val gradientStart: Long,
    val gradientEnd: Long,
    val emoji: String
)

data class TestResult(
    val testType: String,
    val typeCode: String,
    val title: String,
    val nickname: String,
    val description: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val careerFits: List<String>,
    val emoji: String,
    val gradientStart: Long,
    val gradientEnd: Long,
    val confidenceScore: Float,
    val dimensionBreakdown: Map<String, Float>
)
