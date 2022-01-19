package com.dawidk.weatherapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawidk.weatherapp.repository.Repository
import com.dawidk.weatherapp.repository.util.Resource
import com.dawidk.weatherapp.ui.mainscreen.state.MainAction
import com.dawidk.weatherapp.ui.mainscreen.state.MainState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
    private val weatherItemsProvider: WeatherItemsProvider
): ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val state: StateFlow<MainState> = _state

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.LoadLocationWeather -> {
                fetchWeatherData(action.latitude, action.longitude)
            }
        }
    }

    private fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _state.value =
                MainState.Error(exception)
        }) {
            _state.value = MainState.Loading
            _state.value = try {
                val response = repository.getWeatherData(latitude, longitude)
                MainState.DataLoaded(Resource.Success(response))
            } catch (e: Exception) {
                MainState.DataLoaded(Resource.Error(e.localizedMessage))
            }
        }
    }

}