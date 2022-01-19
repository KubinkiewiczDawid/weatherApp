package com.dawidk.weatherapp.ui.mainscreen.state

sealed class MainAction{
    data class LoadLocationWeather(val latitude: Double, val longitude: Double): MainAction()
}