package com.youxiang8727.streamlet.ui.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.ui.components.chart.piechart.PieChart
import com.youxiang8727.streamlet.ui.components.customcalendar.CustomCalendar
import com.youxiang8727.streamlet.ui.screen.transaction.TypeChip
import java.time.LocalDate

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    addNewTransaction: (LocalDate) -> Unit = {}
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    var calendarExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            IconButton(
                onClick = {
                    addNewTransaction(state.date)
                },
                modifier = Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            CustomCalendar(
                modifier = Modifier,
                localDate = state.date,
                expandCallback = {
                    calendarExpanded = it
                },
                callback = {
                    viewModel.onDateChanged(it)
                }
            )

            if (calendarExpanded) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                PieChartLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            TransactionDataList(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            )
        }
    }
}

@Composable
private fun TransactionDataList(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val data = state.transactionData.groupBy {
        it.transactionType
    }

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { (transactionType, transactionDataList) ->
            item {
                Text(
                    text = context.getString(transactionType.stringResourceId)
                )
            }

            items(transactionDataList) { transactionData ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TransactionDataListItem(
                        modifier = Modifier,
                        transactionData = transactionData
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionDataListItem(
    modifier: Modifier = Modifier,
    transactionData: TransactionData
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(6f)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transactionData.category.title,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = transactionData.title,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = transactionData.amount.toString(),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PieChartLayout(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    if (state.pieChartDataType == null) return

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PieChartDataType.defaults.forEach {
                TypeChip(
                    label = context.getString(it.stringResourceId),
                    selected = it == state.pieChartDataType
                ) {
                    viewModel.onPieChartDataTypeChanged(it)
                }
            }
        }

        val data = state.getPieChartData(context)

        if (data.isEmpty()) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(context.getString(R.string.no_data))
            }
            return
        }

        PieChart(
            modifier = modifier,
            data = state.getPieChartData(context)
        )
    }
}