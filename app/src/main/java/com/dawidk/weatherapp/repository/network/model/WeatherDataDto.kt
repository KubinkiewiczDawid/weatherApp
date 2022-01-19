package com.dawidk.weatherapp.repository.network.model

data class WeatherDataDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: WeatherDetailsDto,
    val hourly: SampleRateDataDto,
    val daily: SampleRateDataDto,
    val flags: FlagsDto,
    val offset: Int
)