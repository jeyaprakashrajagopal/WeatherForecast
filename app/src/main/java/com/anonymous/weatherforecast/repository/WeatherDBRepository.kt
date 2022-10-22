package com.anonymous.weatherforecast.repository

import com.anonymous.weatherforecast.data.WeatherAppDao
import com.anonymous.weatherforecast.model.Favorite
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherAppDao: WeatherAppDao) {

    suspend fun getFavorites() = weatherAppDao.getFavorites()

    suspend fun insertFavourite(favorite: Favorite) = weatherAppDao.insertFavorite(favorite)

    suspend fun updateFavourite(favorite: Favorite) = weatherAppDao.updateFavourite(favorite)

    suspend fun getFavoriteById(id: String) = weatherAppDao.getFavoriteById(id)

    suspend fun deleteFavoriteById(id:String) = weatherAppDao.deleteFavouriteById(id)

    suspend fun deleteFavorite(favorite: Favorite) = weatherAppDao.deleteFavourites(favorite)

    suspend fun deleteAllFavorites() = weatherAppDao.deleteFavorites()
}