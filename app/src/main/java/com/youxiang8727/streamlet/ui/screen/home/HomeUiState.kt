package com.youxiang8727.streamlet.ui.screen.home

import android.content.Context
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.mvi.UiState
import com.youxiang8727.streamlet.ui.components.chart.piechart.PieChartData
import java.time.LocalDate

data class HomeUiState(
    val date: LocalDate = LocalDate.now(),
    val transactionData: List<TransactionData> = emptyList(),
    val isCalendarExpand: Boolean = false,
    private val _pieChartDataType: PieChartDataType = PieChartDataType.ByTransactionType(TransactionType.EXPENSE)
): UiState {
    val pieChartDataType: PieChartDataType?
        get() {
            return if (isCalendarExpand) null else _pieChartDataType
        }

    fun getPieChartData(context: Context): List<PieChartData> {
        return when (val type = pieChartDataType) {
            is PieChartDataType.ByTransactionType -> {
                transactionData.filter {
                    it.transactionType == type.transactionType
                }.groupBy {
                    it.category
                }.map {
                    PieChartData(
                        label = it.key.title,
                        data = it.value.sumOf { it.amount },
                        color = it.key.color
                    )
                }
            }
            PieChartDataType.ByIncomeAndExpenseRatio -> {
                transactionData.groupBy {
                    it.transactionType
                }.map {
                    PieChartData(
                        label = context.getString(it.key.stringResourceId),
                        data = it.value.sumOf { it.amount },
                        color = it.key.color
                    )
                }
            }
            null -> emptyList()
        }
    }
}

sealed class PieChartDataType(val stringResourceId: Int) {
    data class ByTransactionType(val transactionType: TransactionType): PieChartDataType(transactionType.stringResourceId)
    object ByIncomeAndExpenseRatio: PieChartDataType(R.string.balance)

    companion object {
        val defaults = listOf(
            ByTransactionType(TransactionType.EXPENSE),
            ByTransactionType(TransactionType.INCOME),
            ByIncomeAndExpenseRatio
        )
    }
}
