package com.dawidk.utils

fun Double.removeSmallerFromBigger(number: Double): Double {
    return if(this < number)
        number - this
    else
        this - number
}