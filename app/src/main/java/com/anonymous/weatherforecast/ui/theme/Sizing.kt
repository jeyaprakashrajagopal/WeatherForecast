package com.anonymous.weatherforecast.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class Sizing(
    val default: TextUnit = 0.sp,
    val extraSmall : TextUnit = 4.sp,
    val small: TextUnit = 8.sp,
    val medium: TextUnit = 4.sp,
    val normal: TextUnit = 18.sp,
    val large: TextUnit = 24.sp,
    val extraLarge: TextUnit = 32.sp
)

val LOCAL_SIZING = compositionLocalOf { Sizing() }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LOCAL_SIZING.current