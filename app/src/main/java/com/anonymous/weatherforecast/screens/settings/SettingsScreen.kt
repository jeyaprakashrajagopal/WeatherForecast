package com.anonymous.weatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel: SettingsViewModel = hiltViewModel()
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val choiceFromDb = viewModel.unitList.collectAsState().value
    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    val defaultChoice =
        if (choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb.first().unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
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
                checked = !unitToggleState,
                onCheckedChange = {
                    unitToggleState = !it
                    choiceState = if (unitToggleState) {
                        "Imperial (F)"
                    } else {
                        "Metric (C)"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RectangleShape)
                    .padding(5.dp)
                    .background(Color.Magenta.copy(alpha = 0.4f))
            ) {
                Text(
                    text = choiceState, color = Color.White, style = MaterialTheme.typography.h6
                )
            }
            Button(onClick = {
                viewModel.deleteAllUnits()
                viewModel.insertUnit(Unit(choiceState))
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