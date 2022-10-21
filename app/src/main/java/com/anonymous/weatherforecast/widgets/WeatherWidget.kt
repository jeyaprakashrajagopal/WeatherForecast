package com.anonymous.weatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.anonymous.weatherforecast.model.WeatherItem
import com.anonymous.weatherforecast.screens.main.WeatherStateImage
import com.anonymous.weatherforecast.utils.formatDateToDay

@Composable
fun CreateDayInformation(it: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${it.weather.first().icon}.png"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.LightGray.copy(0.5f)
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${formatDateToDay(it.dt)}")
            WeatherStateImage(imageUrl = imageUrl)
            Text(
                text = it.weather.first().description,
                modifier = Modifier.background(
                    Color.Red.copy(0.5f),
                    shape = RoundedCornerShape(10.dp)
                ).padding(5.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = FontWeight.ExtraBold
                        )
                        ) {
                            append("${it.temp.min}°")
                        }
                    }
                )
                Text(
                    text = "${it.temp.max}°"
                )
            }
        }
    }
}