package com.typify.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TestResultEntity::class, UserProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TypifyDatabase : RoomDatabase() {
    abstract fun testResultDao(): TestResultDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: TypifyDatabase? = null

        fun getInstance(context: Context): TypifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TypifyDatabase::class.java,
                    "typify.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
