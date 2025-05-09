package com.youxiang8727.streamlet.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.youxiang8727.streamlet.domain.usecase.GetTransactionDataUseCase
import com.youxiang8727.streamlet.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTransactionDataUseCase: GetTransactionDataUseCase
): MviViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState()
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val transactionDataByDateFlow = uiStateFlow.flatMapLatest { state ->
        getTransactionDataUseCase(state.date)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onDateChanged(date: LocalDate) {
        dispatch(
            HomeUiEvent.OnDateChanged(date)
        )
    }

    init {
        viewModelScope.launch {
            transactionDataByDateFlow.collect { transactionData ->
                dispatch(
                    HomeUiEvent.OnTransactionDataChanged(transactionData)
                )
            }
        }
    }

    override fun reduce(event: HomeUiEvent): HomeUiState {
        return when (event) {
            is HomeUiEvent.OnDateChanged -> {
                uiStateFlow.value.copy(
                    date = event.date
                )
            }
            is HomeUiEvent.OnTransactionDataChanged -> {
                uiStateFlow.value.copy(
                    transactionData = event.transactionData
                )
            }
        }
    }
}