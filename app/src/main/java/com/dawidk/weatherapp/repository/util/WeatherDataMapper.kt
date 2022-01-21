package com.dawidk.weatherapp.repository.util

import com.dawidk.weatherapp.repository.domain.model.Flags
import com.dawidk.weatherapp.repository.domain.model.SampleRateData
import com.dawidk.weatherapp.repository.domain.model.WeatherData
import com.dawidk.weatherapp.repository.domain.model.WeatherDetails
import com.dawidk.weatherapp.repository.network.model.FlagsDto
import com.dawidk.weatherapp.repository.network.model.SampleRateDataDto
import com.dawidk.weatherapp.repository.network.model.WeatherDataDto
import com.dawidk.weatherapp.repository.network.model.WeatherDetailsDto

class WeatherDataMapper : DomainMapper<WeatherDataDto, WeatherData> {

    override fun mapToDomainModel(model: WeatherDataDto): WeatherData {
        return WeatherData(
            latitude = model.latitude,
            longitude = model.longitude,
            timezone = model.timezone,
            currently = model.currently?.mapToDomainModel(),
            minutely = model.minutely?.mapToDomainModel(),
            hourly = model.hourly?.mapToDomainModel(),
            daily = model.daily?.mapToDomainModel(),
            flags = model.flags?.mapToDomainModel(),
            offset = model.offset
        )
    }

    override fun mapFromDomainModel(domainModel: WeatherData): WeatherDataDto {
        return WeatherDataDto(
            latitude = domainModel.latitude,
            longitude = domainModel.longitude,
            timezone = domainModel.timezone,
            currently = domainModel.currently?.mapFromDomainModel(),
            minutely = domainModel.minutely?.mapFromDomainModel(),
            hourly = domainModel.hourly?.mapFromDomainModel(),
            daily = domainModel.daily?.mapFromDomainModel(),
            flags = domainModel.flags?.mapFromDomainModel(),
            offset = domainModel.offset
        )
    }
}

fun FlagsDto.mapToDomainModel(): Flags {
    return Flags(meteoAlarmLicense, nearestStation, sources, units)
}

fun WeatherDetailsDto.mapToDomainModel(): WeatherDetails {
    return WeatherDetails(
        apparentTemperature,
        cloudCover,
        dewPoint,
        humidity,
        icon,
        ozone,
        precipAccumulation,
        precipIntensity,
        precipProbability,
        precipType,
        pressure,
        summary,
        temperature,
        time,
        uvIndex,
        visibility,
        windBearing,
        windGust,
        windSpeed,
        apparentTemperatureHigh,
        apparentTemperatureHighTime,
        apparentTemperatureLow,
        apparentTemperatureLowTime,
        apparentTemperatureMax,
        apparentTemperatureMaxTime,
        apparentTemperatureMin,
        apparentTemperatureMinTime,
        moonPhase,
        precipIntensityMax,
        precipIntensityMaxTime,
        sunriseTime,
        sunsetTime,
        temperatureHigh,
        temperatureHighTime,
        temperatureLow,
        temperatureLowTime,
        temperatureMax,
        temperatureMaxTime,
        temperatureMin,
        temperatureMinTime
    )
}

fun SampleRateDataDto.mapToDomainModel(): SampleRateData {
    return SampleRateData(
        weatherDetails = this.weatherDetails.map {
            it.mapToDomainModel()
        },
        icon = icon,
        summary = summary
    )
}

fun Flags.mapFromDomainModel(): FlagsDto {
    return FlagsDto(meteoAlarmLicense, nearestStation, sources, units)
}

fun WeatherDetails.mapFromDomainModel(): WeatherDetailsDto {
    return WeatherDetailsDto(
        apparentTemperature,
        cloudCover,
        dewPoint,
        humidity,
        icon,
        ozone,
        precipAccumulation,
        precipIntensity,
        precipProbability,
        precipType,
        pressure,
        summary,
        temperature,
        time,
        uvIndex,
        visibility,
        windBearing,
        windGust,
        windSpeed,
        apparentTemperatureHigh,
        apparentTemperatureHighTime,
        apparentTemperatureLow,
        apparentTemperatureLowTime,
        apparentTemperatureMax,
        apparentTemperatureMaxTime,
        apparentTemperatureMin,
        apparentTemperatureMinTime,
        moonPhase,
        precipIntensityMax,
        precipIntensityMaxTime,
        sunriseTime,
        sunsetTime,
        temperatureHigh,
        temperatureHighTime,
        temperatureLow,
        temperatureLowTime,
        temperatureMax,
        temperatureMaxTime,
        temperatureMin,
        temperatureMinTime
    )
}

fun SampleRateData.mapFromDomainModel(): SampleRateDataDto {
    return SampleRateDataDto(
        weatherDetails = this.weatherDetails.map {
            it.mapFromDomainModel()
        },
        icon = icon,
        summary = summary
    )
}

