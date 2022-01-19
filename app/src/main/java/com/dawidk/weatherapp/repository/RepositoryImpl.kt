package com.dawidk.weatherapp.repository

import com.dawidk.weatherapp.repository.network.NetworkRepository
import com.dawidk.weatherapp.repository.network.model.WeatherDataDto

class RepositoryImpl(
    private val networkRepository: NetworkRepository
): Repository {

    override suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherDataDto {
        return networkRepository.getWeatherData(latitude, longitude)
    }
}