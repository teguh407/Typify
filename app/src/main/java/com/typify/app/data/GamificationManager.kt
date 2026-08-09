package com.typify.app.data

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Gamification manager — tracks streaks, badges, and stats.
 * Uses SharedPreferences for persistence (offline-first).
 */
class GamificationManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("typify_gamification", 0)
    private val dateFmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    // ── Badges ──────────────────────────────────────────────────
    data class Badge(
        val id: String,
        val name: String,
        val description: String,
        val emoji: String,
        val unlocked: Boolean
    )

    // ── Stats ───────────────────────────────────────────────────
    data class Stats(
        val totalTests: Int,
        val currentStreak: Int,
        val longestStreak: Int,
        val typesDiscovered: List<String>,
        val consistencyScore: Int,
        val lastTestDate: String?,
        val badges: List<Badge>
    )

    // ── Badge Definitions ───────────────────────────────────────
    private val allBadges = listOf(
        Badge("first_test", "First Steps", "Complete your first test", "🎯", false),
        Badge("type_explorer", "Explorer", "Discover 3 different types", "🧭", false),
        Badge("type_master", "Master", "Discover 5+ different types", "👑", false),
        Badge("streak_3", "On Fire", "3-day streak", "🔥", false),
        Badge("streak_7", "Week Warrior", "7-day streak", "⚔️", false),
        Badge("streak_30", "Unstoppable", "30-day streak", "🚀", false),
        Badge("consistent", "Self-Aware", "80%+ type consistency", "🧠", false),
        Badge("retake_master", "Deep Diver", "Take 5 tests", "🔬", false),
        Badge("sharer", "Spread the Word", "Share your result", "📤", false),
        Badge("night_owl", "Night Owl", "Test after midnight", "🦉", false)
    )

    // ── Core: Record Test ───────────────────────────────────────
    fun recordTest(typeCode: String) {
        val today = dateFmt.format(Date())
        val lastDate = prefs.getString(KEY_LAST_TEST_DATE, null)

        // Update streak
        val currentStreak = calculateStreak(today, lastDate)
        val longestStreak = maxOf(currentStreak, prefs.getInt(KEY_LONGEST_STREAK, 0))

        // Update test count
        val totalTests = prefs.getInt(KEY_TOTAL_TESTS, 0) + 1

        // Update types discovered
        val typesSet = prefs.getStringSet(KEY_TYPES_DISCOVERED, emptySet())!!.toMutableSet()
        typesSet.add(typeCode.uppercase())

        // Save
        prefs.edit()
            .putString(KEY_LAST_TEST_DATE, today)
            .putInt(KEY_CURRENT_STREAK, currentStreak)
            .putInt(KEY_LONGEST_STREAK, longestStreak)
            .putInt(KEY_TOTAL_TESTS, totalTests)
            .putStringSet(KEY_TYPES_DISCOVERED, typesSet)
            .apply()

        // Check night owl
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour >= 0 && hour < 4) {
            prefs.edit().putBoolean(KEY_NIGHT_OWL, true).apply()
        }
    }

    private fun calculateStreak(today: String, lastDate: String?): Int {
        if (lastDate == null) return 1

        val todayDate = dateFmt.parse(today)!!
        val lastDateParsed = dateFmt.parse(lastDate)!!

        val diff = (todayDate.time - lastDateParsed.time) / (1000 * 60 * 60 * 24)

        return when {
            diff == 0 -> prefs.getInt(KEY_CURRENT_STREAK, 0).coerceAtLeast(1) // same day
            diff == 1 -> prefs.getInt(KEY_CURRENT_STREAK, 0) + 1 // consecutive day
            else -> 1 // streak broken, start new
        }
    }

    // ── Record Share ────────────────────────────────────────────
    fun recordShare() {
        prefs.edit().putBoolean(KEY_SHARED, true).apply()
    }

    // ── Get Stats ───────────────────────────────────────────────
    fun getStats(): Stats {
        val totalTests = prefs.getInt(KEY_TOTAL_TESTS, 0)
        val currentStreak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        val longestStreak = prefs.getInt(KEY_LONGEST_STREAK, 0)
        val typesDiscovered = prefs.getStringSet(KEY_TYPES_DISCOVERED, emptySet())!!.toList().sorted()
        val lastTestDate = prefs.getString(KEY_LAST_TEST_DATE, null)
        val shared = prefs.getBoolean(KEY_SHARED, false)
        val nightOwl = prefs.getBoolean(KEY_NIGHT_OWL, false)

        // Calculate consistency
        val consistencyScore = calculateConsistency()

        // Determine unlocked badges
        val badges = allBadges.map { badge ->
            val unlocked = when (badge.id) {
                "first_test" -> totalTests >= 1
                "type_explorer" -> typesDiscovered.size >= 3
                "type_master" -> typesDiscovered.size >= 5
                "streak_3" -> longestStreak >= 3
                "streak_7" -> longestStreak >= 7
                "streak_30" -> longestStreak >= 30
                "consistent" -> consistencyScore >= 80
                "retake_master" -> totalTests >= 5
                "sharer" -> shared
                "night_owl" -> nightOwl
                else -> false
            }
            badge.copy(unlocked = unlocked)
        }

        return Stats(
            totalTests = totalTests,
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            typesDiscovered = typesDiscovered,
            consistencyScore = consistencyScore,
            lastTestDate = lastTestDate,
            badges = badges
        )
    }

    private fun calculateConsistency(): Int {
        val types = prefs.getStringSet(KEY_TYPES_DISCOVERED, emptySet())!!
        if (types.size <= 1) return 100
        // If user always gets same type, 100%. More variety = lower consistency.
        val totalTests = prefs.getInt(KEY_TOTAL_TESTS, 0)
        if (totalTests == 0) return 0
        // Most common type frequency / total
        return if (types.size == 1) 100 else maxOf(30, 100 - (types.size - 1) * 15)
    }

    // ── Daily Insight ───────────────────────────────────────────
    fun getDailyInsight(typeCode: String?): String {
        if (typeCode == null) return "Take your first test to unlock daily insights! ✨"

        val detail = TypeDetails.getDetail(typeCode)
            ?: return "Every personality has unique gifts. What's yours? 🌟"

        val insights = detail.dailyInsights
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        return insights[dayOfYear % insights.size]
    }

    // ── Streak Check (for notifications) ────────────────────────
    fun shouldNotifyStreak(): Boolean {
        val today = dateFmt.format(Date())
        val lastDate = prefs.getString(KEY_LAST_TEST_DATE, null)
        if (lastDate == null) return false

        val todayDate = dateFmt.parse(today)!!
        val lastDateParsed = dateFmt.parse(lastDate)!!
        val diff = (todayDate.time - lastDateParsed.time) / (1000 * 60 * 60 * 24)

        // Notify if streak might break (last test was 1 day ago and streak > 2)
        return diff == 1 && prefs.getInt(KEY_CURRENT_STREAK, 0) >= 2
    }

    companion object {
        private const val KEY_LAST_TEST_DATE = "last_test_date"
        private const val KEY_CURRENT_STREAK = "current_streak"
        private const val KEY_LONGEST_STREAK = "longest_streak"
        private const val KEY_TOTAL_TESTS = "total_tests"
        private const val KEY_TYPES_DISCOVERED = "types_discovered"
        private const val KEY_SHARED = "shared_result"
        private const val KEY_NIGHT_OWL = "night_owl"
    }
}
