package com.dawidk.weatherapp.ui.mainscreen.state

import com.dawidk.weatherapp.repository.domain.model.WeatherItem
import com.dawidk.weatherapp.repository.util.Resource

sealed class MainState{
    object Loading : MainState()
    data class DataLoaded(val weatherDataResponse: Resource<List<WeatherItem?>>) : MainState()
    data class Error(val exception: Throwable) : MainState()
}
