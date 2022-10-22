package com.anonymous.weatherforecast.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anonymous.weatherforecast.data.WeatherAppDao
import com.anonymous.weatherforecast.model.Favorite
import com.anonymous.weatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun getDAO(): WeatherAppDao
}