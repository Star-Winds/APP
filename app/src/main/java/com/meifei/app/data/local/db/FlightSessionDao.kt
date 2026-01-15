package com.meifei.app.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: FlightSessionEntity): Long

    @Query("DELETE FROM flight_sessions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM flight_sessions")
    suspend fun deleteAll()

    @Query("SELECT * FROM flight_sessions ORDER BY startAt DESC")
    fun queryAllDesc(): Flow<List<FlightSessionEntity>>

    @Query("SELECT * FROM flight_sessions WHERE startAt BETWEEN :start AND :end ORDER BY startAt DESC")
    suspend fun queryBetween(start: Long, end: Long): List<FlightSessionEntity>

    @Query("SELECT * FROM flight_sessions WHERE startAt >= :start ORDER BY startAt DESC")
    suspend fun queryFrom(start: Long): List<FlightSessionEntity>

    @Query("SELECT * FROM flight_sessions WHERE startAt >= :start ORDER BY startAt DESC")
    suspend fun queryLast24h(start: Long): List<FlightSessionEntity>

    @Query("SELECT * FROM flight_sessions WHERE startAt >= :start ORDER BY startAt DESC")
    suspend fun queryLast7d(start: Long): List<FlightSessionEntity>

    @Query("SELECT * FROM flight_sessions WHERE startAt >= :start ORDER BY startAt DESC")
    suspend fun queryLast30d(start: Long): List<FlightSessionEntity>
}
