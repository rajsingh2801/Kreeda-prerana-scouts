package com.example.kreedaprerana.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "trial_entries",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrialEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val eventType: String,
    val value: Double,
    val unit: String,
    val recordedAt: Long = System.currentTimeMillis()
)
