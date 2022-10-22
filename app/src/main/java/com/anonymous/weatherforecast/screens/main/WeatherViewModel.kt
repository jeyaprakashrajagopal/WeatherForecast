package com.anonymous.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.weatherforecast.data.WeatherResult
import com.anonymous.weatherforecast.model.Weather
import com.anonymous.weatherforecast.repository.WeatherForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherForecastRepository
) : ViewModel() {
    private val _weatherResult =
        MutableStateFlow<WeatherResult<Weather, Boolean, java.lang.Exception>?>(WeatherResult())
    val weatherResult = _weatherResult.asStateFlow()

    fun getWeather(query: String, unit: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _weatherResult.value = _weatherResult.value?.copy(loading = true)
            val result = repository.getWeather(query, unit)
            _weatherResult.value = _weatherResult.value?.copy(data = result.data)
            _weatherResult.value = _weatherResult.value?.copy(loading = false)
        }
    }
}