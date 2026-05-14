package com.example.kreedaprerana.data.local

data class LeaderboardEntry(
    val studentId: Int,
    val studentName: String,
    val sport: String,
    val eventType: String,
    val bestValue: Double,
    val unit: String
)
