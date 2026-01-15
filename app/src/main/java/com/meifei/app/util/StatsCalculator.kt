package com.meifei.app.util

import com.meifei.app.data.local.db.FlightSessionEntity
import java.time.ZoneId

object StatsCalculator {
    data class WindowStats(
        val count: Int,
        val totalDurationMs: Long,
        val averageDurationMs: Long
    )

    /**
     * 统计窗口采用“最近 N 天（含今天）的自然日范围”。
     */
    fun calculateWindowStats(
        sessions: List<FlightSessionEntity>,
        nowMillis: Long,
        days: Int,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): WindowStats {
        val start = TimeUtils.startOfWindowDays(nowMillis, days, zoneId)
        val filtered = sessions.filter { it.startAt >= start && it.startAt <= nowMillis }
        val total = filtered.sumOf { it.durationMs }
        val count = filtered.size
        val average = if (count == 0) 0L else total / count
        return WindowStats(count, total, average)
    }

    /**
     * 判断最近 N 天（含今天）是否每天都有记录。
     */
    fun hasRecordEachDay(
        sessions: List<FlightSessionEntity>,
        nowMillis: Long,
        days: Int,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): Boolean {
        val start = TimeUtils.startOfWindowDays(nowMillis, days, zoneId)
        val sessionDays = sessions
            .filter { it.startAt >= start && it.startAt <= nowMillis }
            .map { TimeUtils.dayKey(it.startAt, zoneId) }
            .toSet()

        val today = TimeUtils.dayKey(nowMillis, zoneId)
        return (0 until days).all { offset ->
            val day = today.minusDays(offset.toLong())
            sessionDays.contains(day)
        }
    }

    fun countLast24h(sessions: List<FlightSessionEntity>, nowMillis: Long): Int {
        val start = nowMillis - 24 * 60 * 60 * 1000L
        return sessions.count { it.startAt >= start && it.startAt <= nowMillis }
    }
}
