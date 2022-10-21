package com.anonymous.weatherforecast.di

import com.anonymous.weatherforecast.network.WeatherForecastAPIService
import com.anonymous.weatherforecast.network.WeatherForecastAPIService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WeatherForecastModule {
    @Provides
    @Singleton
    fun provideRetrofit(): WeatherForecastAPIService =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherForecastAPIService::class.java)
}