package com.dawidk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base class that all MVI ViewModels should extend.
 * @param STATE - The current of the component
 * @param INTENT - Aka action, an intention to change the state of the app. Can be started for e.g by clicking the button
 * @param EVENT - An event type of component for all signals that shouldn't be stored in the state but consumed only once when
 * they happen. Use [Nothing] if effect is not needed in the component
 */

// Needs an upper type because with generics Kotlin assumes that this might be any type - primitive, object or nullable ref
// https://kotlinlang.org/docs/generics.html#generic-constraints
abstract class MviViewModel<STATE: Any, INTENT, EVENT>() : ViewModel() {

    abstract val initialState: STATE
    // or to get rid off warning:
    // private val initialState: STATE by lazy { createInitialState() }
    // abstract fun createInitialState() : STATE

    val currentState: STATE
        get() = _viewState.value

    // stateflow so we always have a state. Also emits last view state when subscribed
    private val _viewState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    // channels because no need to show effect multiple times
    private val _viewEvent = MutableSharedFlow<BaseEvent<EVENT>>()
    val viewEvent = _viewEvent.asSharedFlow()


    abstract fun processIntent(oldState: STATE, intent: INTENT)

    fun processIntent(intent: INTENT) = processIntent(currentState, intent)

    fun postInitialState() = postState(initialState)

    fun postState(state: STATE) {
        _viewState.value = state
    }

    fun postEvent(event: BaseEvent<EVENT>) {
        viewModelScope.launch { _viewEvent.emit(event) }
    }

    fun postEvent(event: EVENT) = postEvent(Event(event))
}
