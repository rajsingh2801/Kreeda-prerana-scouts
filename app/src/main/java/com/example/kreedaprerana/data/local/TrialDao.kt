package com.example.kreedaprerana.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TrialDao {
    @Insert
    suspend fun insertTrial(trial: TrialEntry)

    @Query("SELECT * FROM trial_entries WHERE studentId = :studentId ORDER BY recordedAt DESC")
    fun getTrialsForStudent(studentId: Int): LiveData<List<TrialEntry>>

    @Query("SELECT * FROM trial_entries ORDER BY recordedAt DESC LIMIT 5")
    fun getRecentTrials(): LiveData<List<TrialEntry>>

    @Query("""
        SELECT studentId, s.name as studentName, s.sport, eventType, 
               CASE WHEN unit = 'sec' THEN MIN(value) ELSE MAX(value) END as bestValue, unit
        FROM trial_entries t
        JOIN students s ON t.studentId = s.id
        WHERE eventType = :eventType
        GROUP BY studentId
        ORDER BY CASE WHEN unit = 'sec' THEN MIN(value) END ASC, 
                 CASE WHEN unit = 'm' THEN MAX(value) END DESC
    """)
    fun getLeaderboard(eventType: String): LiveData<List<LeaderboardEntry>>

    @Query("SELECT COUNT(*) FROM trial_entries")
    fun getTrialCount(): LiveData<Int>
}
