package com.dawidk.weatherapp.ui.mainscreen

import androidx.lifecycle.viewModelScope
import com.dawidk.mvi.MviViewModel
import com.dawidk.weatherapp.repository.Repository
import com.dawidk.weatherapp.repository.domain.WeatherItemsProvider
import com.dawidk.weatherapp.repository.util.Resource
import com.dawidk.weatherapp.ui.mainscreen.state.MainIntent
import com.dawidk.weatherapp.ui.mainscreen.state.MainEvent
import com.dawidk.weatherapp.ui.mainscreen.state.MainState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: Repository,
    private val weatherItemsProvider: WeatherItemsProvider
): MviViewModel<MainState, MainIntent, MainEvent>() {

    override val initialState: MainState
        get() = MainState.Loading

    override fun processIntent(oldState: MainState, intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadLocationWeather -> {
                fetchWeatherData(intent.latitude, intent.longitude)
            }
            is MainIntent.NavigateToAboutScreen -> navigateToAboutScreen()
        }
    }

    private fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            postState(MainState.Error(exception))
        }) {
            postState(MainState.Loading)
            postState(try {
                val response = repository.getWeatherData(latitude, longitude)
                MainState.DataLoaded(Resource.Success(weatherItemsProvider(response)))
            } catch (e: Exception) {
                MainState.DataLoaded(Resource.Error(e.localizedMessage))
            })
        }
    }

    private fun navigateToAboutScreen() {
        postEvent(MainEvent.NavigateToAboutScreen)
    }
}
