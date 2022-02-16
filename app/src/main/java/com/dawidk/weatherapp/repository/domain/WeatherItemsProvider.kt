package com.dawidk.weatherapp.repository.domain

import com.dawidk.utils.fahrenheitToCelsius
import com.dawidk.weatherapp.repository.domain.model.*

class WeatherItemsProvider {

    operator fun invoke(weatherData: WeatherData): List<WeatherItem?> {
        return listOf(weatherData.currently?.let { insertCurrentWeatherData(it) },
            weatherData.hourly?.let { insertTimeSamplesWeatherItem(it, SampleType.HOURLY) },
            weatherData.daily?.let { insertTimeSamplesWeatherItem(it, SampleType.DAILY) })
    }

    private fun insertCurrentWeatherData(currently: WeatherDetails, sampleType: SampleType? = null): WeatherItem.CurrentWeatherItem {
        return with(currently){
                WeatherItem.CurrentWeatherItem(
                    time = time,
                    summary = summary,
                    icon = icon,
                    precipIntensity = precipIntensity,
                    precipProbability = precipProbability,
                    precipType = precipType,
                    temperature = temperature?.fahrenheitToCelsius(),
                    apparentTemperature = apparentTemperature?.fahrenheitToCelsius(),
                    humidity = humidity,
                    pressure = pressure,
                    windSpeed = windSpeed,
                    cloudCover = cloudCover,
                    uvIndex = uvIndex,
                    sampleType = sampleType,
                )
            }
    }

    private fun insertTimeSamplesWeatherItem(sampleRateData: SampleRateData, sampleType: SampleType): WeatherItem.TimeSamplesWeatherItem {
        return with(sampleRateData){
            WeatherItem.TimeSamplesWeatherItem(
                weatherDetails = weatherDetails.map {
                    insertCurrentWeatherData(it, sampleType)
                },
                summary = summary,
                icon = icon
            )
        }
    }
}