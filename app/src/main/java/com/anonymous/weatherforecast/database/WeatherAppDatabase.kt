package com.anonymous.weatherforecast.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anonymous.weatherforecast.data.WeatherAppDao
import com.anonymous.weatherforecast.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun getDAO(): WeatherAppDao
}