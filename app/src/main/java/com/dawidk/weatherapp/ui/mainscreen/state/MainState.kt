package com.dawidk.weatherapp.ui.mainscreen.state

import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.util.Resource

sealed class MainState{
    object Loading : MainState()
    //TODO: Change from DTO oject here to domain model object
    data class DataLoaded(val weatherDataResponse: Resource<WeatherData>) : MainState()
    data class Error(val exception: Throwable) : MainState()
}
