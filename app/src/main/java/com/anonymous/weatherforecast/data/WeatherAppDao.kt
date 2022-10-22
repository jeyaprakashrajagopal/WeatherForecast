package com.anonymous.weatherforecast.data

import androidx.room.*
import com.anonymous.weatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherAppDao {
    @Query("SELECT * from favourite_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from favourite_table where id=:id")
    fun getFavoriteById(id: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favorite: Favorite)

    @Query("DELETE from favourite_table where id=:id")
    suspend fun deleteFavouriteById(id: String)

    @Delete
    suspend fun deleteFavourites(favorite: Favorite)

    @Query("DELETE from favourite_table")
    suspend fun deleteFavorites()
}