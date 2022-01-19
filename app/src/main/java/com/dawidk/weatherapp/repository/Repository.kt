package com.dawidk.weatherapp.repository

import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.util.WeatherDataMapper

interface Repository {

    val weatherDataMapper: WeatherDataMapper
    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherData

}