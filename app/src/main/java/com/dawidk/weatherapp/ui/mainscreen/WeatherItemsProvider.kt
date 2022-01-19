package com.dawidk.weatherapp.ui.mainscreen

import com.dawidk.weatherapp.repository.domain.model.SampleRateData
import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.domain.model.WeatherDetails
import com.dawidk.weatherapp.repository.domain.model.WeatherItem

class WeatherItemsProvider {

    operator fun invoke(weatherData: WeatherData): List<WeatherItem> {
        return listOf(insertCurrentWeatherData(weatherData.currently),
                insertTimeSamplesWeatherItem(weatherData.hourly),
                insertTimeSamplesWeatherItem(weatherData.daily))

    }

    private fun insertCurrentWeatherData(currently: WeatherDetails): WeatherItem.CurrentWeatherItem {
        return with(currently){
                WeatherItem.CurrentWeatherItem(
                    time = time,
                    summary = summary,
                    icon = icon,
                    precipIntensity = precipIntensity,
                    precipProbability = precipProbability,
                    precipType = precipType,
                    temperature = temperature,
                    apparentTemperature = apparentTemperature,
                    humidity = humidity,
                    pressure = pressure,
                    windSpeed = windSpeed,
                    cloudCover = cloudCover,
                    uvIndex = uvIndex
                )
            }
    }

    private fun insertTimeSamplesWeatherItem(sampleRateData: SampleRateData): WeatherItem.TimeSamplesWeatherItem {
        return with(sampleRateData){
            WeatherItem.TimeSamplesWeatherItem(
                weatherDetails = weatherDetails.map {
                    insertCurrentWeatherData(it)
                },
                summary = summary,
                icon = icon
            )
        }
    }


}