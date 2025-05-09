package com.youxiang8727.streamlet.ui.screen.home

import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.mvi.UiEvent
import java.time.LocalDate

sealed interface HomeUiEvent: UiEvent {
    data class OnDateChanged(val date: LocalDate): HomeUiEvent

    data class OnTransactionDataChanged(val transactionData: List<TransactionData>): HomeUiEvent
}