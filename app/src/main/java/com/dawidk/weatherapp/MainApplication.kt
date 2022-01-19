package com.dawidk.weatherapp

import android.app.Application
import com.dawidk.weatherapp.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            fragmentFactory()
            modules(
                appModule
            )
        }
    }

}