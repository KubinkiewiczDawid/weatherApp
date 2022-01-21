package com.dawidk.weatherapp.repository.network.model

import com.google.gson.annotations.SerializedName

data class SampleRateDataDto(
    @SerializedName("data")val weatherDetails: List<WeatherDetailsDto>,
    val icon: String?,
    val summary: String
)