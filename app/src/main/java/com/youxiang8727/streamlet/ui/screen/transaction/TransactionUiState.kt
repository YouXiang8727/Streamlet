package com.youxiang8727.streamlet.ui.screen.transaction

import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.mvi.UiState
import java.time.LocalDate

data class TransactionUiState(
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val date: LocalDate = LocalDate.now(),
    val title: String = "",
    val amount: Int = 0,
    val note: String = "",
    val categories: List<Category> = emptyList(),
    val categoryEntity: Category? = null
): UiState {
    val maxTitleLength = 10
    val titleSupportText = "${title.length}/$maxTitleLength"

    val maxAmount: Int = 99999999

    val maxNoteLength = 50
    val noteSupportText = "${note.length}/$maxNoteLength"

    val saveable = title.isNotBlank() && amount > 0
}
