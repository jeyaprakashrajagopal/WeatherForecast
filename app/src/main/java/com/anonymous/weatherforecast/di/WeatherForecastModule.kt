package com.anonymous.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.anonymous.weatherforecast.database.WeatherAppDatabase
import com.anonymous.weatherforecast.network.WeatherForecastAPIService
import com.anonymous.weatherforecast.network.WeatherForecastAPIService.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WeatherAppDatabase::class.java, "Weather Database")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(weatherAppDatabase: WeatherAppDatabase) = weatherAppDatabase.getDAO()
}