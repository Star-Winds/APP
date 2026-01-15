package com.meifei.app

import com.google.common.truth.Truth.assertThat
import com.meifei.app.data.local.db.FlightSessionEntity
import com.meifei.app.util.TitleResolver
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class TitleResolverTest {
    private val zoneId = ZoneId.of("Asia/Shanghai")

    @Test
    fun `title resolves to fei sheng when 7 days daily and also 24h count high`() {
        val now = LocalDate.of(2024, 5, 30).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val sessions = (0 until 7).map { offset ->
            val day = LocalDate.of(2024, 5, 30).minusDays(offset.toLong())
            sessionOnDay(day)
        } + listOf(
            sessionOnDay(LocalDate.of(2024, 5, 30), 2)
        )

        val result = TitleResolver.resolveTitle(sessions, now + 3_600_000)
        assertThat(result.title).isEqualTo("飞圣")
    }

    @Test
    fun `title resolves to fei kuang when 30 days daily`() {
        val now = LocalDate.of(2024, 6, 1).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val sessions = (0 until 30).map { offset ->
            sessionOnDay(LocalDate.of(2024, 6, 1).minusDays(offset.toLong()))
        }

        val result = TitleResolver.resolveTitle(sessions, now + 10_000)
        assertThat(result.title).isEqualTo("飞狂")
    }

    @Test
    fun `title resolves to duck when only one in 7 days`() {
        val now = LocalDate.of(2024, 6, 1).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val sessions = listOf(sessionOnDay(LocalDate.of(2024, 5, 31)))

        val result = TitleResolver.resolveTitle(sessions, now + 10_000)
        assertThat(result.title).isEqualTo("鸭嘴兽泰瑞")
    }

    private fun sessionOnDay(day: LocalDate, index: Int = 0): FlightSessionEntity {
        val startAt = day.atTime(9 + index, 0).atZone(zoneId).toInstant().toEpochMilli()
        val endAt = startAt + 30 * 60 * 1000L
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
