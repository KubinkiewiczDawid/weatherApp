package com.dawidk.weatherapp.repository.network.model

data class WeatherDataDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: WeatherDetailsDto,
    val hourly: HourlyDto,
    val daily: DailyDto,
    val flags: FlagsDto,
    val offset: Int
)