package com.dawidk.weatherapp.repository.network.model

data class SampleRateDataDto(
    val weatherDetails: List<WeatherDetailsDto>,
    val icon: String,
    val summary: String
)