package com.youxiang8727.streamlet.mvi

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface UiState

interface UiEvent

private const val BUFFER_SIZE = 64

abstract class MviViewModel<S: UiState, E: UiEvent>(
    initialState: S
): ViewModel() {
    var state: S by mutableStateOf(initialState)
        private set

    private var event: MutableSharedFlow<E> = MutableSharedFlow(
        extraBufferCapacity = BUFFER_SIZE
    )

    fun dispatch(event: E) {
        viewModelScope.launch {
            this@MviViewModel.event.emit(event)
        }
    }

    abstract fun reduce(event: E): S

    init {
        viewModelScope.launch {
            event.collect {
                state = reduce(it)
            }
        }
    }
}