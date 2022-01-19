package com.dawidk.weatherapp.repository.network.service

import com.dawidk.weatherapp.repository.network.model.WeatherDataDto
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("{latitude},{longitude}")
    suspend fun getWeatherData(
        @Path("latitude")latitude: Double,
        @Path("longitude")longitude: Double
    ): WeatherDataDto

}