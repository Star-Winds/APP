package com.meifei.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.meifei.app.data.AppRepository
import com.meifei.app.data.local.db.FlightSessionEntity
import com.meifei.app.util.StatsCalculator
import com.meifei.app.util.TitleResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {
    private val nowFlow = MutableStateFlow(System.currentTimeMillis())

    val uiState: StateFlow<UiState> = combine(
        repository.sessions,
        repository.flightState,
        nowFlow
    ) { sessions, flightState, nowMillis ->
        val stats7 = StatsCalculator.calculateWindowStats(sessions, nowMillis, 7)
        val stats30 = StatsCalculator.calculateWindowStats(sessions, nowMillis, 30)
        val title = TitleResolver.resolveTitle(sessions, nowMillis)
        UiState(
            isFlying = flightState.isFlying,
            startAt = flightState.startAt,
            locationText = flightState.locationText.orEmpty(),
            note = flightState.note.orEmpty(),
            sessions = sessions,
            stats7 = stats7,
            stats30 = stats30,
            title = title
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.empty())

    fun refreshNow() {
        nowFlow.value = System.currentTimeMillis()
    }

    fun startFlight(locationText: String, note: String) {
        viewModelScope.launch {
            val startAt = System.currentTimeMillis()
            repository.startFlight(startAt, locationText.ifBlank { null }, note.ifBlank { null })
        }
    }

    fun updateNotes(locationText: String, note: String) {
        viewModelScope.launch {
            repository.updateNotes(locationText.ifBlank { null }, note.ifBlank { null })
        }
    }

    fun endFlight() {
        viewModelScope.launch {
            val state = uiState.value
            if (!state.isFlying || state.startAt <= 0L) return@launch
            val endAt = System.currentTimeMillis()
            val duration = endAt - state.startAt
            val session = FlightSessionEntity(
                startAt = state.startAt,
                endAt = endAt,
                durationMs = duration,
                locationText = state.locationText.ifBlank { null },
                note = state.note.ifBlank { null }
            )
            repository.insertSession(session)
            repository.endFlight()
        }
    }

    fun deleteSession(id: Long) {
        viewModelScope.launch {
            repository.deleteSession(id)
        }
    }

    fun deleteAllSessions() {
        viewModelScope.launch {
            repository.deleteAllSessions()
        }
    }

    data class UiState(
        val isFlying: Boolean,
        val startAt: Long,
        val locationText: String,
        val note: String,
        val sessions: List<FlightSessionEntity>,
        val stats7: StatsCalculator.WindowStats,
        val stats30: StatsCalculator.WindowStats,
        val title: TitleResolver.TitleResult
    ) {
        companion object {
            fun empty() = UiState(
                isFlying = false,
                startAt = 0L,
                locationText = "",
                note = "",
                sessions = emptyList(),
                stats7 = StatsCalculator.WindowStats(0, 0L, 0L),
                stats30 = StatsCalculator.WindowStats(0, 0L, 0L),
                title = TitleResolver.TitleResult("飞哥", "默认")
            )
        }
    }
}

class MainViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
