package com.dawidk.weatherapp.repository.network.model

data class HourlyDto(
    val weatherDetails: List<WeatherDetailsDto>,
    val icon: String,
    val summary: String
)