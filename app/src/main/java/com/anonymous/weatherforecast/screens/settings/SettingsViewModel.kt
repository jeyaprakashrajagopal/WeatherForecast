package com.anonymous.weatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.weatherforecast.model.Unit
import com.anonymous.weatherforecast.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherDBRepository: WeatherDBRepository) :
    ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(
        emptyList()
    )
    val unitList = _unitList.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepository.getUnits().collect {
                if (it.isNotEmpty()) {
                    _unitList.value = it
                } else {
                    _error.value = "empty"
                }
            }
        }
    }

    fun insertUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) { weatherDBRepository.insertUnit(unit) }

    fun deleteUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) { weatherDBRepository.deleteUnit(unit) }

    fun deleteAllUnits() =
        viewModelScope.launch(Dispatchers.IO) { weatherDBRepository.deleteAllUnits() }

    fun updateUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) { weatherDBRepository.updateUnits(unit) }
}