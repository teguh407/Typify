package com.typify.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TestResultDao {
    @Query("SELECT * FROM test_results ORDER BY takenAt DESC")
    fun getAll(): Flow<List<TestResultEntity>>

    @Query("SELECT * FROM test_results WHERE testType = :testType ORDER BY takenAt DESC LIMIT 1")
    fun getLatestByType(testType: String): Flow<TestResultEntity?>

    @Insert
    suspend fun insert(result: TestResultEntity)

    @Query("DELETE FROM test_results WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT COUNT(*) FROM test_results")
    fun getCount(): Flow<Int>
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE key = :key")
    suspend fun get(key: String): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(entry: UserProfileEntity)

    @Query("DELETE FROM user_profile WHERE key = :key")
    suspend fun delete(key: String)
}
