package com.youxiang8727.streamlet.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
@Preview
fun CalendarDateItem (
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
    selectedDate: LocalDate = LocalDate.now(),
    color: CalendarDateItemColor = CalendarDateItemDefault.colors(),
    callback: (LocalDate) -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color.getBackgroundColor(date, selectedDate),
                RoundedCornerShape(8.dp)
            ).clickable {
                callback(date)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = date.dayOfWeek.name.take(3),
                color = color.getTextColor(date, selectedDate),
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = date.dayOfMonth.toString(),
                color = color.getTextColor(date, selectedDate),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

data class CalendarDateItemColor(
    val selectedBackgroundColor: Color,
    val unselectedBackgroundColor: Color,
    val sameMonthTextColor: Color,
    val differentMonthTextColor: Color
) {
    fun getBackgroundColor(date: LocalDate, selectedDate: LocalDate): Color {
        return when {
            date == selectedDate -> selectedBackgroundColor
            else -> unselectedBackgroundColor
        }
    }

    fun getTextColor(date: LocalDate, selectedDate: LocalDate): Color {
        return when {
            date.year == selectedDate.year && date.month == selectedDate.month -> sameMonthTextColor
            else -> differentMonthTextColor
        }
    }
}

object CalendarDateItemDefault {
    @Composable
    fun colors(): CalendarDateItemColor = CalendarDateItemColor(
        selectedBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        unselectedBackgroundColor = Color.Transparent,
        sameMonthTextColor = MaterialTheme.colorScheme.onBackground,
        differentMonthTextColor = Color.Gray
    )
}

