package com.anonymous.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Int): String {
    val formatter = SimpleDateFormat("EEE, MMM dd", Locale.GERMAN)
    val date = Date(timestamp.toLong() * 1000)
    return formatter.format(date)
}

fun formatDateToDay(timestamp: Int): String {
    val formatter = SimpleDateFormat("EEE", Locale.GERMAN)
    val date = Date(timestamp.toLong() * 1000)
    return formatter.format(date)
}

fun formatTime(timestamp: Int): String {
    val formatter = SimpleDateFormat("HH:MM:dd", Locale.GERMAN)
    val date = Date(timestamp.toLong() * 1000)
    return formatter.format(date)
}