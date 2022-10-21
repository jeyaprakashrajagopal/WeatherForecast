package com.anonymous.weatherforecast.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

@Composable
fun DashedHorizontalLine(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    pathEffect: PathEffect
) {
    Canvas(modifier = modifier, onDraw = {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    })
}

@Composable
fun DashedVerticalLine(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    pathEffect: PathEffect
) {
    Canvas(modifier = modifier, onDraw = {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            pathEffect = pathEffect
        )
    })
}