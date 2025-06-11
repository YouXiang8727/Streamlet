package com.youxiang8727.streamlet.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface UiState

interface UiEvent

private const val BUFFER_SIZE = 64

abstract class MviViewModel<S: UiState, E: UiEvent>(
    initialState: S
): ViewModel() {
    private val _uiStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val uiStateFlow: StateFlow<S> = _uiStateFlow.asStateFlow()

//    var state: S by mutableStateOf(initialState)
//        private set

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
//                state = reduce(it)
                Log.d(this@MviViewModel.javaClass.simpleName, "${this@MviViewModel} event.collect($it)")
                _uiStateFlow.value = reduce(it)
            }
        }

        Log.d(this@MviViewModel.javaClass.simpleName, "${this@MviViewModel} initialState($initialState)")
    }
}