package com.youxiang8727.streamlet.ui.components.chart.piechart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<PieChartData>
) {
    val sum = data.sumOf { it.data }.toFloat()
    Row(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Canvas(modifier = modifier.fillMaxHeight().aspectRatio(1f)) {
            var startAngle = -90f

            data.forEach {
                val sweepAngle = 360 * it.data / sum
                drawArc(
                    color = it.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true)
                startAngle += sweepAngle
            }
        }

        LazyColumn (
            modifier = Modifier.fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            items(data) {
                val percentage = String.format(Locale.getDefault(), "%.2f%%", it.data / sum * 100)
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp)
                            .background(it.color)
                    )

                    Text(
                        text = it.label + percentage,
                        color = it.color,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

data class PieChartData(
    val label: String,
    val data: Int,
    val color: Color,
)