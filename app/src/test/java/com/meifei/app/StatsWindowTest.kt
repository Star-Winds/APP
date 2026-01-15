package com.meifei.app

import com.google.common.truth.Truth.assertThat
import com.meifei.app.data.local.db.FlightSessionEntity
import com.meifei.app.util.StatsCalculator
import com.meifei.app.util.TimeUtils
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class StatsWindowTest {
    private val zoneId = ZoneId.of("Asia/Shanghai")

    @Test
    fun `window includes today and previous 6 days`() {
        val today = LocalDate.of(2024, 6, 10)
        val now = today.atTime(12, 0).atZone(zoneId).toInstant().toEpochMilli()

        val inWindow = (0 until 7).map { offset ->
            sessionOnDay(today.minusDays(offset.toLong()))
        }
        val outOfWindow = sessionOnDay(today.minusDays(7))

        val stats = StatsCalculator.calculateWindowStats(inWindow + outOfWindow, now, 7, zoneId)
        assertThat(stats.count).isEqualTo(7)
    }

    @Test
    fun `window start uses natural day start`() {
        val today = LocalDate.of(2024, 6, 10)
        val now = today.atTime(0, 30).atZone(zoneId).toInstant().toEpochMilli()
        val startDay = today.minusDays(6)

        val startOfWindow = TimeUtils.startOfWindowDays(now, 7, zoneId)
        val expected = startDay.atStartOfDay(zoneId).toInstant().toEpochMilli()
        assertThat(startOfWindow).isEqualTo(expected)
    }

    private fun sessionOnDay(day: LocalDate): FlightSessionEntity {
        val startAt = day.atTime(8, 0).atZone(zoneId).toInstant().toEpochMilli()
        val endAt = startAt + 20 * 60 * 1000L
        return FlightSessionEntity(
            id = 0L,
            startAt = startAt,
            endAt = endAt,
            durationMs = endAt - startAt,
            locationText = null,
            note = null
        )
    }
}
