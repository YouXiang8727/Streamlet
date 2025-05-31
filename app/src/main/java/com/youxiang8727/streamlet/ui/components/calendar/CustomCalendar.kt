package com.youxiang8727.streamlet.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youxiang8727.streamlet.ui.components.calendar.CalendarUtils.getMonthCalendar
import java.time.LocalDate

@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    expandCallback: (Boolean) -> Unit = {},
    callback: (LocalDate) -> Unit = {}
) {
    var year by remember(localDate) { mutableIntStateOf(localDate.year) }
    var month by remember(localDate) { mutableStateOf(localDate.month) }
    var expand by remember { mutableStateOf(false) }

    LaunchedEffect(expand) {
        expandCallback(expand)
    }

    val dates by remember(year, month) {
        derivedStateOf {
            LocalDate.of(year, month, 1).getMonthCalendar()
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
            NormalCalendar(
                modifier = Modifier.fillMaxWidth(),
                localDate = localDate,
                dates = dates,
                callback = callback
            )
        } else {
            SingleLineCalendar(
                modifier = Modifier.fillMaxWidth(),
                localDate = localDate,
                dates = dates,
                callback = callback
            )
        }
    }
}