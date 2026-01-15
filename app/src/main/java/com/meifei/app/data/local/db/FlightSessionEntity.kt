package com.meifei.app.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flight_sessions")
data class FlightSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val startAt: Long,
    val endAt: Long,
    val durationMs: Long,
    val locationText: String?,
    val note: String?
)
