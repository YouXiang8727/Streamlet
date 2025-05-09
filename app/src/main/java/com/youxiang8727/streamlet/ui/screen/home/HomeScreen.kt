package com.youxiang8727.streamlet.ui.screen.home


import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.ui.components.customcalendar.CustomCalendar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CustomCalendar(
            modifier = modifier,
            localDate = state.date
        ) {
            viewModel.onDateChanged(it)
        }

        Spacer(modifier = Modifier.weight(1f))

        TransactionDataList(modifier = modifier)
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
            .fillMaxWidth()
            .aspectRatio(1.2f)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { (transactionType, transactionDataList) ->
            item {
                Text(
                    text = context.getString(transactionType.id)
                )
            }

            items(transactionDataList) { transactionData ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TransactionDataListItem(
                        modifier = modifier,
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
        modifier = modifier.fillMaxWidth()
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