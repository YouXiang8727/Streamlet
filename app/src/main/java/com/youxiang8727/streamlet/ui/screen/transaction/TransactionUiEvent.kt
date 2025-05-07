package com.youxiang8727.streamlet.ui.screen.transaction

import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.mvi.UiEvent
import java.time.LocalDate

sealed interface TransactionUiEvent: UiEvent {
    data class OnTransactionTypeChanged(val transactionType: TransactionType, val categories: List<Category>): TransactionUiEvent

    data class OnCategoryChanged(val category: Category): TransactionUiEvent

    data class OnDateChanged(val date: LocalDate): TransactionUiEvent

    data class OnTitleChanged(val title: String): TransactionUiEvent

    data class OnAmountChanged(val amount: Int): TransactionUiEvent

    data class OnNoteChanged(val note: String): TransactionUiEvent
}