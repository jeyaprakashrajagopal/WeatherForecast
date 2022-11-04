package com.anonymous.weatherforecast.repository

import com.anonymous.weatherforecast.data.WeatherResult
import com.anonymous.weatherforecast.network.WeatherForecastAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val weatherForecastAPIService: WeatherForecastAPIService
) {
    suspend fun getWeather(
        query: String,
        unit: String
    ): Flow<WeatherResult> = flow {
        emit(WeatherResult.Loading)
        val result = weatherForecastAPIService.getWeather(query, unit)
        emit(WeatherResult.Success(result))
    }.catch { exception ->
        emit(WeatherResult.Failed(error = exception.message.toString()))
    }
}