package com.anonymous.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.weatherforecast.data.WeatherResult
import com.anonymous.weatherforecast.repository.WeatherForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherForecastRepository
) : ViewModel() {
    private val _weatherResult =
        MutableSharedFlow<WeatherResult>(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val weatherResult = _weatherResult.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _weatherResult.tryEmit(WeatherResult.Failed(throwable.message.toString()))
    }

    fun getWeather(query: String, unit: String) {
        if (query.isBlank()) return
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getWeather(query, unit).collect {
                _weatherResult.emit(it)
            }
        }
    }
}