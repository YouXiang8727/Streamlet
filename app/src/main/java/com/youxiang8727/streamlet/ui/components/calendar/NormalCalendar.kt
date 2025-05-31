package com.youxiang8727.streamlet.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun NormalCalendar(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    dates: List<LocalDate>,
    callback: (LocalDate) -> Unit = {

    }
) {
    // Weekday headers
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DayOfWeek.entries.forEach {
            Text(
                text = it.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }

    Spacer(Modifier.height(4.dp))

    // Full calendar view
    dates.chunked(7).forEach { week ->
        Row(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(7f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            week.forEach { date ->
                CalendarDateItem(
                    modifier = Modifier.aspectRatio(1f),
                    date = date,
                    selectedDate = localDate,
                ) {
                    callback(it)
                }
            }
        }
    }
}