package com.youxiang8727.streamlet.ui.screen.home

import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.mvi.UiState
import java.time.LocalDate

data class HomeUiState(
    val date: LocalDate = LocalDate.now(),
    val transactionData: List<TransactionData> = emptyList()
): UiState
