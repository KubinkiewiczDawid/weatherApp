package com.dawidk.weatherapp.repository.domain.model

sealed class WeatherItem{
    data class CurrentWeatherItem(
        val time: Long?,
        val summary: String?,
        val icon: String?,
        val precipIntensity: Double?,
        val precipProbability: Double?,
        val precipType: String?,
        val temperature: Double?,
        val apparentTemperature: Double?,
        val humidity: Double?,
        val pressure: Double?,
        val windSpeed: Double?,
        val cloudCover: Double?,
        val uvIndex: Int?,
        val sampleType: SampleType? = null,
    ): WeatherItem()

    data class TimeSamplesWeatherItem(
        val weatherDetails: List<CurrentWeatherItem>,
        val icon: String?,
        val summary: String?
    ): WeatherItem()
}

enum class SampleType{
    MINUTELY, HOURLY, DAILY
}