package com.anonymous.weatherforecast.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.weatherforecast.model.Favorite
import com.anonymous.weatherforecast.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository) :
    ViewModel() {
    private val _favoritesList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoritesList = _favoritesList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable.message.toString()
    }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            weatherDBRepository.getFavorites().distinctUntilChanged().collect {
                if (it.isNotEmpty()) {
                    _favoritesList.value = it
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            weatherDBRepository.insertFavourite(favorite)
            val data = _favoritesList.value.map { favoriteItem ->
                if (favoriteItem.id == favorite.id) favorite else favoriteItem
            }
            _favoritesList.update {
                data
            }
        }
    }

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            weatherDBRepository.updateFavourite(favorite)
            _favoritesList.update {
                it.map { favoriteItem ->
                    if (favoriteItem.id == favorite.id) favorite else favoriteItem
                }
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            weatherDBRepository.deleteFavorite(favorite)

            _favoritesList.update {
                it.filter { favoriteParam ->
                    favoriteParam.id != favorite.id
                }
            }
        }
    }
}