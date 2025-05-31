package com.youxiang8727.streamlet.ui.components.calendar

import java.time.DayOfWeek
import java.time.LocalDate

object CalendarUtils {
    fun LocalDate.getMonthCalendar(): List<LocalDate> {
        val firstDayOfMonth = this.withDayOfMonth(1)
        val firstDayOfCalendar = generateSequence(firstDayOfMonth) { it.minusDays(1) }
            .first { it.dayOfWeek == DayOfWeek.MONDAY }

        val lastDayOfMonth = this.withDayOfMonth(this.lengthOfMonth())
        val lastDayOfCalendar = generateSequence(lastDayOfMonth) { it.plusDays(1) }
            .first { it.dayOfWeek == DayOfWeek.SUNDAY }

        val result = generateSequence(firstDayOfCalendar) { date ->
            if (date < lastDayOfCalendar) date.plusDays(1) else null
        }.toList()

        return result
    }
}