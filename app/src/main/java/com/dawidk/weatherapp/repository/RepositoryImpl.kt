package com.dawidk.weatherapp.repository

import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.network.NetworkRepository
import com.dawidk.weatherapp.repository.util.WeatherDataMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    weatherDataMapper: WeatherDataMapper
): Repository {

    override val weatherDataMapper: WeatherDataMapper = WeatherDataMapper()

    override suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherData {
        return weatherDataMapper.mapToDomainModel(
            networkRepository.getWeatherData(latitude, longitude)
        )
    }
}