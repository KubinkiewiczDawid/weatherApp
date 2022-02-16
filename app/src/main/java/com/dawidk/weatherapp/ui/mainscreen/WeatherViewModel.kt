package com.dawidk.weatherapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dawidk.weatherapp.repository.Repository
import com.dawidk.weatherapp.repository.domain.WeatherItemsProvider
import com.dawidk.weatherapp.repository.domain.model.WeatherItem
import com.dawidk.weatherapp.repository.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: Repository,
    private val weatherItemsProvider: WeatherItemsProvider
) : ViewModel() {

    private val _weatherDataResponse: MutableStateFlow<Resource<List<WeatherItem?>>> =
        MutableStateFlow(Resource.Loading())
    val weatherDataResponse = _weatherDataResponse.asStateFlow()

    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _weatherDataResponse.value =
                Resource.Error(exception.localizedMessage)
        }) {
            _weatherDataResponse.value = Resource.Loading()
            _weatherDataResponse.value = try {
                val response = repository.getWeatherData(latitude, longitude)
                Resource.Success(weatherItemsProvider(response))
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }
}
