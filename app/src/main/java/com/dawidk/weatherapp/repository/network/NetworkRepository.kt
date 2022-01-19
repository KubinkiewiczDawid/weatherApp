package com.dawidk.weatherapp.repository.network

import com.dawidk.weatherapp.repository.network.model.WeatherDataDto
import com.dawidk.weatherapp.repository.network.service.WeatherService

class NetworkRepository(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherDataDto {
        return weatherService.getWeatherData(latitude, longitude)
    }

}