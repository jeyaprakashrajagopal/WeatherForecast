package com.anonymous.weatherforecast.repository

import com.anonymous.weatherforecast.data.WeatherResult
import com.anonymous.weatherforecast.model.Weather
import com.anonymous.weatherforecast.network.WeatherForecastAPIService
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val weatherForecastAPIService: WeatherForecastAPIService
) {
    suspend fun getWeather(query: String): WeatherResult<Weather, Boolean, Exception> = try {
        val result = weatherForecastAPIService.getWeather(query)
        WeatherResult(data = result)
    } catch (e: Exception) {
        WeatherResult(error = e)
    }
}