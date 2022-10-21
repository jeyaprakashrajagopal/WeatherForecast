package com.anonymous.weatherforecast.data

data class WeatherResult<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var error: E? = null
)