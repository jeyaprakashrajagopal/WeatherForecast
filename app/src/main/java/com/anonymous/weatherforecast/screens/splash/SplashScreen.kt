package com.anonymous.weatherforecast.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anonymous.weatherforecast.R
import com.anonymous.weatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    val sizeChanges = remember {
        mutableStateOf(0.dp)
    }
    val size = animateDpAsState(targetValue = sizeChanges.value, animationSpec = tween(durationMillis = 1000, easing = {
        OvershootInterpolator(8f).getInterpolation(it)
    }))

    LaunchedEffect(key1 = true) {
        scale.animateTo(0.9f, animationSpec = tween(durationMillis = 1000, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        sizeChanges.value += 90.dp
        delay(2000L)
        navController.navigate(WeatherScreens.MainScreen.name + "/ ")
    }

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "Sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(size.value)
            )
            Text(
                text = "Find the sun?",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}