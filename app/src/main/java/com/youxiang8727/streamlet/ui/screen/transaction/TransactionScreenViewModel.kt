package com.youxiang8727.streamlet.ui.screen.transaction

import androidx.lifecycle.viewModelScope
import com.youxiang8727.streamlet.data.model.Category
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.usecase.GetCategoriesUseCase
import com.youxiang8727.streamlet.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
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
        dispatch(TransactionUiEvent.OnTitleChanged(title))
    }

    fun onAmountChanged(amount: Int) {
        dispatch(TransactionUiEvent.OnAmountChanged(amount))
    }

    init {
        onTransactionTypeChanged(TransactionType.EXPENSE)
    }

    override fun reduce(event: TransactionUiEvent): TransactionUiState {
        return when (event) {
            is TransactionUiEvent.OnTransactionTypeChanged -> {
                state.copy(
                    transactionType = event.transactionType,
                    categories = event.categories,
                    category = event.categories.firstOrNull()
                )
            }
            is TransactionUiEvent.OnCategoryChanged -> {
                state.copy(
                    category = event.category
                )
            }
            is TransactionUiEvent.OnAmountChanged -> {
                state.copy(
                    amount = event.amount
                )
            }
            is TransactionUiEvent.OnDateChanged -> {
                state.copy(
                    date = event.date
                )
            }
            is TransactionUiEvent.OnTitleChanged -> {
                state.copy(
                    title = event.title
                )
            }
        }
    }
}