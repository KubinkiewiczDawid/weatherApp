package com.dawidk.weatherapp.repository.network.model

data class DailyDto(
    val weatherDetails: List<WeatherDetailsDto>,
    val icon: String,
    val summary: String
)