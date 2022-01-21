package com.dawidk.utils

fun Double.removeSmallerFromBigger(number: Double): Double {
    return if(this < number)
        number - this
    else
        this - number
}

fun Double.fahrenheitToCelsius(): Double{
    return  (this - 32) * 0.5556
}