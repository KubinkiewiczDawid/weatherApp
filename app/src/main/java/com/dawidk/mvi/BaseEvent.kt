package com.dawidk.mvi

sealed class BaseEvent<T>

data class Event<T>(val data: T) : BaseEvent<T>()
data class CommonEvent<T>(val data: CommonEventData) : BaseEvent<T>()

sealed class CommonEventData {
    object UnknownError : CommonEventData()
}
