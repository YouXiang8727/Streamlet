package com.youxiang8727.streamlet.ui.screen.transaction

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.domain.usecase.GetCategoriesUseCase
import com.youxiang8727.streamlet.domain.usecase.SaveTransactionDataUseCase
import com.youxiang8727.streamlet.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val saveTransactionDataUseCase: SaveTransactionDataUseCase
): MviViewModel<TransactionUiState, TransactionUiEvent>(
    initialState = TransactionUiState()
) {
    fun onTransactionTypeChanged(transactionType: TransactionType) {
        viewModelScope.launch {
            val categories = getCategoriesUseCase(transactionType)
            dispatch(
                TransactionUiEvent.OnTransactionTypeChanged(transactionType, categories)
            )
        }
    }

    fun onCategoryChanged(category: Category) {
        dispatch(TransactionUiEvent.OnCategoryChanged(category))
    }

    fun onDateChanged(date: LocalDate) {
        dispatch(TransactionUiEvent.OnDateChanged(date))
    }

    fun onTitleChanged(title: String) {
        if (title.length > uiStateFlow.value.maxTitleLength) return
        dispatch(TransactionUiEvent.OnTitleChanged(title))
    }

    fun onAmountChanged(amount: Int) {
        if (amount > uiStateFlow.value.maxAmount) return
        dispatch(TransactionUiEvent.OnAmountChanged(amount))
    }

    fun onNoteChanged(note: String) {
        if (note.length > uiStateFlow.value.maxNoteLength) return
        dispatch(TransactionUiEvent.OnNoteChanged(note))
    }

    @SuppressLint("StringFormatMatches")
    fun save() {
        viewModelScope.launch {
            val transactionData = TransactionData(
                transactionType = uiStateFlow.value.transactionType,
                category = uiStateFlow.value.categoryEntity!!,
                date = uiStateFlow.value.date,
                title = uiStateFlow.value.title,
                amount = uiStateFlow.value.amount,
                note = uiStateFlow.value.note
            )

            try {
                saveTransactionDataUseCase(transactionData)
                dispatch(
                    TransactionUiEvent.OnSaveResult(
                        context.getString(R.string.transaction_saved_successfully)
                    )
                )
            }catch (e: Exception) {
                dispatch(
                    TransactionUiEvent.OnSaveResult(
                        context.getString(
                            R.string.transaction_saved_failed,
                            e.toString()
                        )
                    )
                )
            }
        }
    }

    init {
        onTransactionTypeChanged(TransactionType.EXPENSE)
    }

    override fun reduce(event: TransactionUiEvent): TransactionUiState {
        return when (event) {
            is TransactionUiEvent.OnTransactionTypeChanged -> {
                uiStateFlow.value.copy(
                    transactionType = event.transactionType,
                    categories = event.categories,
                    categoryEntity = event.categories.firstOrNull()
                )
            }
            is TransactionUiEvent.OnCategoryChanged -> {
                uiStateFlow.value.copy(
                    categoryEntity = event.category
                )
            }
            is TransactionUiEvent.OnAmountChanged -> {
                uiStateFlow.value.copy(
                    amount = event.amount
                )
            }
            is TransactionUiEvent.OnDateChanged -> {
                uiStateFlow.value.copy(
                    date = event.date
                )
            }
            is TransactionUiEvent.OnTitleChanged -> {
                uiStateFlow.value.copy(
                    title = event.title,
                    message = null
                )
            }
            is TransactionUiEvent.OnNoteChanged -> {
                uiStateFlow.value.copy(
                    note = event.note
                )
            }
            is TransactionUiEvent.OnSaveResult -> {
                uiStateFlow.value.copy(
                    title = "",
                    amount = 0,
                    note = "",
                    message = event.message
                )
            }
        }
    }
}