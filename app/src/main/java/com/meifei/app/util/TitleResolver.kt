package com.meifei.app.util

import com.meifei.app.data.local.db.FlightSessionEntity

object TitleResolver {
    data class TitleResult(val title: String, val rule: String)

    /**
     * 称号优先级（高 → 低）：
     * 1) 飞狂：30 天每日都有记录
     * 2) 飞圣：7 天每日都有记录
     * 3) 飞仙：24h 内次数 ≥ 2
     * 4) 鸭嘴兽泰瑞：7 天次数 ≤ 1
     * 5) 小佛：7 天次数 < 3
     * 6) 飞哥：兜底（7 日内不是每日起飞的）
     */
    fun resolveTitle(sessions: List<FlightSessionEntity>, nowMillis: Long): TitleResult {
        val has30 = StatsCalculator.hasRecordEachDay(sessions, nowMillis, 30)
        if (has30) {
            return TitleResult("飞狂", "30 天每日都有起飞记录")
        }

        val has7 = StatsCalculator.hasRecordEachDay(sessions, nowMillis, 7)
        if (has7) {
            return TitleResult("飞圣", "7 天每日都有起飞记录")
        }

        val count24h = StatsCalculator.countLast24h(sessions, nowMillis)
        if (count24h >= 2) {
            return TitleResult("飞仙", "24 小时内起飞次数 ≥ 2")
        }

        val stats7 = StatsCalculator.calculateWindowStats(sessions, nowMillis, 7)
        if (stats7.count <= 1) {
            return TitleResult("鸭嘴兽泰瑞", "7 天内起飞次数 ≤ 1")
        }

        if (stats7.count < 3) {
            return TitleResult("小佛", "7 天内起飞次数 < 3")
        }

        return TitleResult("飞哥", "7 天内不是每日起飞，默认称号")
    }
}
