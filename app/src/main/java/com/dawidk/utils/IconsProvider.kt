package com.dawidk.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.dawidk.weatherapp.R

fun String.toIcon(resources: Resources): Drawable? {
    return when(this){
        "sunny" -> ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)
        "partly-cloudy-day" -> ResourcesCompat.getDrawable(resources, R.drawable.partly_cloudy_day, null)
        "partly-cloudy-night" -> ResourcesCompat.getDrawable(resources, R.drawable.partly_cloudy_night, null)
        "cloudy" -> ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)
        "rain" -> ResourcesCompat.getDrawable(resources, R.drawable.umbrella, null)
        "fog" -> ResourcesCompat.getDrawable(resources, R.drawable.blur, null)
        "wind" -> ResourcesCompat.getDrawable(resources, R.drawable.windy, null)
        else -> ResourcesCompat.getDrawable(resources, R.drawable.placeholder_img, null)
    }
}