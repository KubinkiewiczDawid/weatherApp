package com.dawidk.weatherapp.repository.domain.model

import com.google.gson.annotations.SerializedName

data class Flags(
    val meteoAlarmLicense: String?,
    val nearestStation: Double?,
    val sources: List<String>?,
    val units: String?
)