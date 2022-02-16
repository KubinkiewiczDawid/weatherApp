package com.dawidk.weatherapp.di

import com.dawidk.weatherapp.repository.Repository
import com.dawidk.weatherapp.repository.RepositoryImpl
import com.dawidk.weatherapp.repository.network.NetworkRepository
import com.dawidk.weatherapp.repository.network.service.WeatherService
import com.dawidk.weatherapp.repository.util.WeatherDataMapper
import com.dawidk.weatherapp.ui.mainscreen.WeatherViewModel
import com.dawidk.weatherapp.repository.domain.WeatherItemsProvider
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

//TODO: Hide this api key
private const val BASE_URL = "https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/"

val appModule = module {
    viewModel { WeatherViewModel(get(), get()) }
    single{ createRetrofit() }
    single { createWeatherService(get()) }
    single { WeatherDataMapper() }
    single<Repository> {
        RepositoryImpl(
            networkRepository = get(),
            weatherDataMapper = get()
        )
    }
    single {
        NetworkRepository(
            weatherService = get()
        )
    }
    single { WeatherItemsProvider() }
}

private fun createRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC).redactHeader("Retrofit response:")
    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(client)
        .build()
}

private fun createWeatherService(retrofit: Retrofit): WeatherService {
    return retrofit.create(WeatherService::class.java)
}
