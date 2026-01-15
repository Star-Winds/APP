package com.meifei.app.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeUtils {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun formatDateTime(millis: Long, zoneId: ZoneId = ZoneId.systemDefault()): String {
        return Instant.ofEpochMilli(millis).atZone(zoneId).format(dateTimeFormatter)
    }

    fun formatDuration(millis: Long): String {
        val totalSeconds = millis.coerceAtLeast(0L) / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    fun startOfDay(millis: Long, zoneId: ZoneId = ZoneId.systemDefault()): Long {
        val date = Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate()
        return date.atStartOfDay(zoneId).toInstant().toEpochMilli()
    }

    fun dayKey(millis: Long, zoneId: ZoneId = ZoneId.systemDefault()): LocalDate {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate()
    }

    fun startOfWindowDays(nowMillis: Long, days: Int, zoneId: ZoneId = ZoneId.systemDefault()): Long {
        val today = dayKey(nowMillis, zoneId)
        val startDay = today.minusDays((days - 1).toLong())
        return startDay.atStartOfDay(zoneId).toInstant().toEpochMilli()
    }
}
