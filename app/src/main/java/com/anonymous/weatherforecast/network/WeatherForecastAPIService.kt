package com.anonymous.weatherforecast.network

import com.anonymous.weatherforecast.model.Weather
import com.anonymous.weatherforecast.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastAPIService {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("units") unit: String = "imperial",
        @Query("appid") appId: String = API_KEY
    ): Weather
}