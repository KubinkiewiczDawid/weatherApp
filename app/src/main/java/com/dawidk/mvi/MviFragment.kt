package com.dawidk.mvi

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class MviFragment<STATE : Any, INTENT, EVENT, ViewModel : MviViewModel<STATE, INTENT, EVENT>> :
    Fragment() {

    abstract val viewModel: ViewModel

    abstract fun registerStateListener(viewState: STATE)

    abstract fun registerEventListener(viewEvent: EVENT)

    private fun renderCommonEvent(commonEvent: CommonEventData) {
        when (commonEvent) {
            is CommonEventData.UnknownError -> Unit // Do something
        }
    }

    fun initializeBaseObservers(clearState: Boolean = false) {
        viewModel.apply {
            if (clearState) postInitialState()
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect { state ->
                        registerStateListener(state)
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewEvent.collect { event ->
                        when (event) {
                            is CommonEvent -> renderCommonEvent(event.data)
                            is Event -> registerEventListener(event.data)
                        }
                    }
                }
            }
        }
    }

    fun postIntent(intent: INTENT) = viewModel.processIntent(intent)
}
