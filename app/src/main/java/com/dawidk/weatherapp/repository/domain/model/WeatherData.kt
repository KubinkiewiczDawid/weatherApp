package com.dawidk.weatherapp.repository.domain.model

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: WeatherDetails,
    val hourly: SampleRateData,
    val daily: SampleRateData,
    val flags: Flags,
    val offset: Int
)