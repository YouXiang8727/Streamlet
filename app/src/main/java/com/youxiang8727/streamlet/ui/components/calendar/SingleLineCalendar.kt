package com.youxiang8727.streamlet.ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun SingleLineCalendar(
    modifier: Modifier = Modifier,
    localDate: LocalDate = LocalDate.now(),
    dates: List<LocalDate>,
    callback: (LocalDate) -> Unit = {

    }
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(localDate) {
        if (dates.indexOf(localDate) != -1) {
            lazyListState.animateScrollToItem(dates.indexOf(localDate))
        }
    }

    LazyRow(
        modifier = modifier.fillMaxWidth()
            .aspectRatio(7f),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = lazyListState
    ) {
        items(dates) {
            CalendarDateItem(
                modifier = Modifier.fillMaxHeight()
                    .aspectRatio(1f),
                date = it,
                selectedDate = localDate,
            ) {
                callback(it)
            }
        }
    }
}