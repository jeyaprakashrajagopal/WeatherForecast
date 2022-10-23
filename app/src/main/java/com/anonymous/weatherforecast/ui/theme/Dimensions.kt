package com.anonymous.weatherforecast.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val dimen0_25: Dp = 2.dp,
    val dimen0_50: Dp = 4.dp,
    val dimen0_75: Dp = 6.dp,
    val dimen1: Dp = 8.dp,
    val dimen1_25: Dp = 10.dp,
    val dimen1_50: Dp = 12.dp,
    val dimen1_75: Dp = 14.dp,
    val dimen2: Dp = 16.dp,
    val dimen2_25: Dp = 18.dp,
    val dimen2_50: Dp = 20.dp,
    val dimen2_75: Dp = 22.dp,
    val dimen3: Dp = 24.dp,
)

val LOCAL_DIMENSIONS = compositionLocalOf { Dimensions() }

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LOCAL_DIMENSIONS.current