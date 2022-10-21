package com.anonymous.weatherforecast.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.navigation.WeatherScreens

@Composable
fun CreateTopAppBar(
    title: String,
    isMainScreen: Boolean = true,
    elevation: Dp,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More icon")
                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            if (!isMainScreen) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back arrow")
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}
