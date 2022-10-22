package com.anonymous.weatherforecast.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.anonymous.weatherforecast.R
import com.anonymous.weatherforecast.components.CircularProgressBar
import com.anonymous.weatherforecast.data.WeatherResult
import com.anonymous.weatherforecast.model.Weather
import com.anonymous.weatherforecast.model.WeatherItem
import com.anonymous.weatherforecast.utils.formatDate
import com.anonymous.weatherforecast.utils.formatTime
import com.anonymous.weatherforecast.widgets.CreateDayInformation
import com.anonymous.weatherforecast.widgets.WeatherAppToolBar

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: WeatherViewModel = hiltViewModel(),
    city: String
) {
    val weatherData = mainViewModel.weatherResult.collectAsState()
    if (city.trim().isNotBlank()) {
        mainViewModel.getWeather(city)
    } else {
        mainViewModel.getWeather("Offenbach")
    }

    WeatherAppToolBar(
        navController = navController,
        title = if (weatherData.value?.data?.city != null) {
            "${weatherData.value?.data?.city?.name},${weatherData.value?.data?.city?.country}"
        } else {
            "WeatherForeCast"
        }
    ) {
        CreateContent(weatherData)
    }
}

@Composable
private fun CreateContent(weatherData: State<WeatherResult<Weather, Boolean, java.lang.Exception>?>) {
    if (weatherData.value?.loading == true) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressBar(size = 150.dp, strokeWidth = 2.dp, color = Color.Green)
        }
    } else {
        if (weatherData.value?.data != null) {
            CreateWeatherInformation(weatherData = weatherData)
        }
    }
}

@Composable
fun CreateWeatherInformation(weatherData: State<WeatherResult<Weather, Boolean, Exception>?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${weatherData.value?.data?.list?.first()?.dt?.let { formatDate(it) }}",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        CreateWeatherWidget(weatherData)
        CreateWindPressure(weatherData.value?.data)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        CreateSunRiseAndSunSet(weatherData.value?.data)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        ShowWeatherForWeek(weatherData.value?.data?.list)
    }
}

@Composable
fun ShowWeatherForWeek(list: List<WeatherItem>?) {
    if (list != null) {
        LazyColumn {
            items(list) {
                CreateDayInformation(it)
            }
        }
    }
}

@Composable
fun CreateSunRiseAndSunSet(data: Weather?) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${
                    data?.list?.first()?.sunrise?.let { formatTime(it) }
                }",
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${data?.list?.first()?.sunset?.let { formatTime(it) }}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun CreateWindPressure(data: Weather?) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${data?.list?.first()?.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${data?.list?.first()?.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${data?.list?.first()?.speed} mph",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun CreateWeatherWidget(weatherData: State<WeatherResult<Weather, Boolean, Exception>?>) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${weatherData.value?.data?.list?.first()?.weather?.first()?.icon}.png"
    Surface(modifier = Modifier.size(200.dp), shape = CircleShape, color = Color.Yellow) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WeatherStateImage(imageUrl = imageUrl)
            Text(
                text = "${
                    weatherData.value?.data?.list?.first()?.temp?.day?.let {
                        formatDecimals(
                            it
                        )
                    }
                }"
                        + "°",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${weatherData.value?.data?.list?.first()?.weather?.first()?.main}",
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier
            .size(80.dp)
            .then(modifier)
    )
}

fun formatDecimals(value: Double): String = "%.0f".format(value)