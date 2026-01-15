package com.meifei.app.data

import com.meifei.app.data.local.datastore.FlightPreferences
import com.meifei.app.data.local.db.FlightSessionDao
import com.meifei.app.data.local.db.FlightSessionEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val dao: FlightSessionDao,
    private val preferences: FlightPreferences
) {
    val sessions: Flow<List<FlightSessionEntity>> = dao.queryAllDesc()
    val flightState = preferences.state

    suspend fun insertSession(session: FlightSessionEntity) = dao.insert(session)

    suspend fun deleteSession(id: Long) = dao.deleteById(id)

    suspend fun deleteAllSessions() = dao.deleteAll()

    suspend fun startFlight(startAt: Long, locationText: String?, note: String?) {
        preferences.startFlight(startAt, locationText, note)
    }

    suspend fun updateNotes(locationText: String?, note: String?) {
        preferences.updateNotes(locationText, note)
    }

    suspend fun endFlight() {
        preferences.endFlight()
    }

    suspend fun queryBetween(start: Long, end: Long): List<FlightSessionEntity> {
        return dao.queryBetween(start, end)
    }

    suspend fun queryLast24h(start: Long): List<FlightSessionEntity> {
        return dao.queryLast24h(start)
    }

    suspend fun queryLast7d(start: Long): List<FlightSessionEntity> {
        return dao.queryLast7d(start)
    }

    suspend fun queryLast30d(start: Long): List<FlightSessionEntity> {
        return dao.queryLast30d(start)
    }
}
