package com.typify.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_results")
data class TestResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val testType: String,
    val resultType: String,
    val resultTitle: String,
    val resultDescription: String,
    val takenAt: Long = System.currentTimeMillis(),
    val questionsAnswered: Int,
    val confidenceScore: Float
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val key: String,
    val value: String
)
