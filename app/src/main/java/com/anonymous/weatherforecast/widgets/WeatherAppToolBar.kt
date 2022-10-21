package com.anonymous.weatherforecast.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.screens.main.CreateTopAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateAppTopBar(
    navController: NavHostController,
    title: String,
    isMainScreen: Boolean = true,
    content: @Composable () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CreateTopAppBar(
            title = title,
            elevation = 5.dp,
            isMainScreen = isMainScreen,
            navController = navController
        )
    }) {
        content()
    }
}