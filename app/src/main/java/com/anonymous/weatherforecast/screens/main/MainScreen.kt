package com.anonymous.weatherforecast.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.anonymous.weatherforecast.screens.favorites.FavoriteViewModel
import com.anonymous.weatherforecast.screens.settings.SettingsViewModel
import com.anonymous.weatherforecast.ui.theme.dimensions
import com.anonymous.weatherforecast.utils.formatDate
import com.anonymous.weatherforecast.utils.formatTime
import com.anonymous.weatherforecast.widgets.CreateDayInformation
import com.anonymous.weatherforecast.widgets.WeatherAppToolBar

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: WeatherViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    city: String
) {
    val weatherData = mainViewModel.weatherResult.collectAsState(initial = null)
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    val curCity: String = city.ifBlank { "Offenbach" }
    val unit =
        if (unitFromDb.isNotEmpty()) unitFromDb.first().unit.split(" ").first()
            .lowercase() else "imperial"

    LaunchedEffect(key1 = curCity, key2 = unit) {
        mainViewModel.getWeather(curCity, unit)
    }

    when (weatherData.value) {
        is WeatherResult.Loading -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressBar(
                size = 100.dp,
                strokeWidth = MaterialTheme.dimensions.dimen1,
                color = Color.Magenta
            )
        }
        is WeatherResult.Failed -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = (weatherData.value as WeatherResult.Failed).error,
                style = MaterialTheme.typography.h3
            )
        }
        is WeatherResult.Success -> CreateWeatherToolBar(
            navController = navController,
            weatherData = (weatherData.value as WeatherResult.Success).data,
            favoriteViewModel = favoriteViewModel
        )
        null -> Unit
    }
}

@Composable
private fun CreateWeatherToolBar(
    navController: NavHostController,
    weatherData: Weather?,
    favoriteViewModel: FavoriteViewModel
) {
    WeatherAppToolBar(
        navController = navController,
        title = if (weatherData?.city != null) {
            "${weatherData.city.name},${weatherData.city.country}"
        } else {
            "WeatherForeCast"
        },
        favoriteViewModel = favoriteViewModel
    ) {
        CreateContent(weatherData)
    }
}

@Composable
private fun CreateContent(weatherData: Weather?) {
    if (weatherData != null) {
        CreateWeatherInformation(weatherData = weatherData)
    }
}

@Composable
fun CreateWeatherInformation(weatherData: Weather?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.dimen1_25),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${weatherData?.list?.first()?.dt?.let { formatDate(it) }}",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.dimen1_25))
        CreateWeatherWidget(weatherData)
        CreateWindPressure(weatherData)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        CreateSunRiseAndSunSet(weatherData)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        ShowWeatherForWeek(weatherData?.list)
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
fun CreateWeatherWidget(weatherData: Weather?) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${weatherData?.list?.first()?.weather?.first()?.icon}.png"
    Surface(modifier = Modifier.size(200.dp), shape = CircleShape, color = Color.Yellow) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WeatherStateImage(imageUrl = imageUrl)
            Text(
                text = "${
                    weatherData?.list?.first()?.temp?.day?.let {
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
                text = "${weatherData?.list?.first()?.weather?.first()?.main}",
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