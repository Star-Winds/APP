package com.meifei.app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("flight_prefs")

class FlightPreferences(private val context: Context) {
    private val isFlyingKey = booleanPreferencesKey("is_flying")
    private val startAtKey = longPreferencesKey("current_start_at")
    private val locationKey = stringPreferencesKey("current_location")
    private val noteKey = stringPreferencesKey("current_note")

    val state: Flow<FlightState> = context.dataStore.data.map { prefs ->
        FlightState(
            isFlying = prefs[isFlyingKey] ?: false,
            startAt = prefs[startAtKey] ?: 0L,
            locationText = prefs[locationKey],
            note = prefs[noteKey]
        )
    }

    suspend fun startFlight(startAt: Long, locationText: String?, note: String?) {
        context.dataStore.edit { prefs ->
            prefs[isFlyingKey] = true
            prefs[startAtKey] = startAt
            setNullable(prefs, locationKey, locationText)
            setNullable(prefs, noteKey, note)
        }
    }

    suspend fun updateNotes(locationText: String?, note: String?) {
        context.dataStore.edit { prefs ->
            setNullable(prefs, locationKey, locationText)
            setNullable(prefs, noteKey, note)
        }
    }

    suspend fun endFlight() {
        context.dataStore.edit { prefs ->
            prefs[isFlyingKey] = false
            prefs[startAtKey] = 0L
            prefs.remove(locationKey)
            prefs.remove(noteKey)
        }
    }

    private fun setNullable(
        prefs: androidx.datastore.preferences.core.MutablePreferences,
        key: androidx.datastore.preferences.core.Preferences.Key<String>,
        value: String?
    ) {
        if (value.isNullOrBlank()) {
            prefs.remove(key)
        } else {
            prefs[key] = value
        }
    }
}

data class FlightState(
    val isFlying: Boolean,
    val startAt: Long,
    val locationText: String?,
    val note: String?
)
