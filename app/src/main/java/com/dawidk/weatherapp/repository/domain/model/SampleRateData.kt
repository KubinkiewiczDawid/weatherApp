package com.dawidk.weatherapp.repository.domain.model

data class SampleRateData(
    val weatherDetails: List<WeatherDetails>,
    val icon: String,
    val summary: String
)