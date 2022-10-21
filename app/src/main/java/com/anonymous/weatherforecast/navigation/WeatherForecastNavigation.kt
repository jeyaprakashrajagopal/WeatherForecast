package com.anonymous.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anonymous.weatherforecast.screens.*
import com.anonymous.weatherforecast.screens.main.MainScreen
import com.anonymous.weatherforecast.screens.main.WeatherViewModel

@Composable
fun WeatherForecastNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(route = WeatherScreens.MainScreen.name+"/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            }
        )) { navBack ->
            val city = navBack.arguments?.getString("city")
            if(city != null) {
                val viewModel: WeatherViewModel = hiltViewModel()
                MainScreen(navController, viewModel, city)
            }
        }
        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController)
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
    }
}