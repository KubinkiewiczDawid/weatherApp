package com.dawidk.weatherapp.repository

import com.dawidk.weatherapp.repository.network.model.WeatherDataDto

interface Repository {

    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherDataDto

}