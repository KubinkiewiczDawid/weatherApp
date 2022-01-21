package com.dawidk.weatherapp.repository.network.model

import com.google.gson.annotations.SerializedName

data class FlagsDto(
    @SerializedName("meteoalarm-license")val meteoAlarmLicense: String?,
    @SerializedName("nearest-station")val nearestStation: Double?,
    val sources: List<String>?,
    val units: String?
)