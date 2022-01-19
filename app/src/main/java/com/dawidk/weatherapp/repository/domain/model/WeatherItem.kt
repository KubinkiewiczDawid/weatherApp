package com.dawidk.weatherapp.repository.domain.model

sealed class WeatherItem{
    data class CurrentWeatherItem(
        val time: Int,
        val summary: String,
        val icon: String,
        val precipIntensity: Double?,
        val precipProbability: Double,
        val precipType: String?,
        val temperature: Double,
        val apparentTemperature: Double,
        val humidity: Double,
        val pressure: Double,
        val windSpeed: Double,
        val cloudCover: Double,
        val uvIndex: Int,
    ): WeatherItem()

    data class TimeSamplesWeatherItem(
        val currentWeatherItem: List<CurrentWeatherItem>,
        val icon: String,
        val summary: String
    ): WeatherItem()
}