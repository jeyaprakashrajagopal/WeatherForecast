package com.anonymous.weatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.R
import com.anonymous.weatherforecast.model.Unit
import com.anonymous.weatherforecast.widgets.WeatherAppToolBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val choiceFromDb = viewModel.unitList.collectAsState().value
    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    var isToggleState by remember {
        mutableStateOf(false)
    }
    val defaultChoice =
        if (choiceFromDb.isNotEmpty()) choiceFromDb.first().unit else measurementUnits[0]

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    val scope = rememberCoroutineScope()

    WeatherAppToolBar(navController = navController, title = "Settings", isMainScreen = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add unit icon")
            Text(
                text = stringResource(id = R.string.change_unit_text_label),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp)
            )
            IconToggleButton(
                checked = !isToggleState, onCheckedChange = {
                    isToggleState = !it
                    choiceState = if (isToggleState) measurementUnits[0] else measurementUnits[1]
                }, modifier = Modifier
                    .background(Color.Magenta.copy(0.4f))
                    .padding(5.dp)
                    .clip(
                        RectangleShape
                    )
            ) {
                Text(text = choiceState, color = Color.White, style = MaterialTheme.typography.h6)
            }

            Button(onClick = {
                scope.launch {
                    withContext(Dispatchers.IO) { viewModel.deleteAllUnits() }
                    withContext(Dispatchers.IO) {
                        viewModel.insertUnit(
                            Unit(choiceState)
                        )
                    }
                }
            }) {
                Text(
                    text = stringResource(id = R.string.save_button_label),
                    style = MaterialTheme.typography.h6,
                    fontSize = 18.sp
                )
            }
        }
    }
}