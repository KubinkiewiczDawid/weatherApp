package com.dawidk.weatherapp.ui.mainscreen.state

sealed class MainIntent{
    data class LoadLocationWeather(val latitude: Double, val longitude: Double): MainIntent()
    object NavigateToAboutScreen: MainIntent()
}
