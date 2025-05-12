package com.youxiang8727.streamlet.ui.components.customcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    expandCallback: (Boolean) -> Unit = {},
    callback: (LocalDate) -> Unit = {}
) {
    var year by remember { mutableIntStateOf(localDate.year) }
    var month by remember { mutableStateOf(localDate.month) }
    var expand by remember { mutableStateOf(false) }

    LaunchedEffect(expand) {
        expandCallback(expand)
    }

    val dates by remember {
        derivedStateOf {
            val start = LocalDate.of(year, month, 1)
            if (expand) {
                start.getMonthCalendar()
            } else {
                listOf(
                    (1..start.lengthOfMonth()).map {
                        LocalDate.of(year, month, it)
                    }
                )
            }
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                val previous = LocalDate.of(year, month, 1).minusMonths(1)
                year = previous.year
                month = previous.month
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
            }

            TextButton(
                onClick = {
                    expand = !expand
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${month.name.lowercase().replaceFirstChar { it.titlecase() }} $year",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            IconButton(onClick = {
                val next = LocalDate.of(year, month, 1).plusMonths(1)
                year = next.year
                month = next.month
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null)
            }
        }

        Spacer(Modifier.height(8.dp))

        if (expand) {
            // Weekday headers
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            dates.forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    week.forEach { date ->
                        CalendarDateItem(
                            modifier = Modifier.weight(1f),
                            targetDate = localDate,
                            date = date,
                            body = {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (date.month == month) Color.Unspecified else Color.Gray
                                )
                            }
                        ) {
                            callback(it)
                        }
                    }
                }
            }
        } else {
            val lazyListState = rememberLazyListState()

            val visible by remember {
                derivedStateOf {
                    val index = dates.first().indexOf(localDate)

                    lazyListState.layoutInfo.visibleItemsInfo.any {
                        it.index == index
                    }
                }
            }

            LaunchedEffect(visible) {
                if (visible.not()) {
                    val index = dates.first().indexOf(localDate)
                    lazyListState.animateScrollToItem(index)
                }
            }
            // Compact view
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7f),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(dates.first()) { date ->
                    CalendarDateItem(
                        modifier = Modifier.padding(4.dp),
                        targetDate = localDate,
                        date = date,
                        body = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = date.dayOfWeek.name.take(3), style = MaterialTheme.typography.labelSmall)
                                Text(text = date.dayOfMonth.toString(), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    ) {
                        callback(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDateItem (
    modifier: Modifier = Modifier,
    targetDate: LocalDate,
    date: LocalDate,
    body: @Composable () -> Unit,
    callback: (LocalDate) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                if (targetDate == date) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
                RoundedCornerShape(8.dp)
            ).clickable {
                callback(date)
            },
        contentAlignment = Alignment.Center
    ) {
        body()
    }
}


private fun LocalDate.getMonthCalendar(): List<List<LocalDate>> {
    val firstDayOfMonth = this.withDayOfMonth(1)
    val firstDayOfCalendar = generateSequence(firstDayOfMonth) { it.minusDays(1) }
        .first { it.dayOfWeek == DayOfWeek.MONDAY }

    val lastDayOfMonth = this.withDayOfMonth(this.lengthOfMonth())
    val lastDayOfCalendar = generateSequence(lastDayOfMonth) { it.plusDays(1) }
        .first { it.dayOfWeek == DayOfWeek.SUNDAY }

    val result = generateSequence(firstDayOfCalendar) { date ->
        if (date < lastDayOfCalendar) date.plusDays(1) else null
    }.toList().chunked(7)

    return result
}