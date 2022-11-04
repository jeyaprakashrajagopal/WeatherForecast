package com.anonymous.weatherforecast.data

import com.anonymous.weatherforecast.model.Weather

sealed class WeatherResult {
    object Loading : WeatherResult()
    data class Success(val data: Weather) : WeatherResult()
    data class Failed(val error: String) : WeatherResult()
}
