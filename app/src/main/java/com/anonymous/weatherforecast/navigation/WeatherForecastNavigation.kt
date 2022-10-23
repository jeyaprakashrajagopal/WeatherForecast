package com.anonymous.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anonymous.weatherforecast.screens.AboutScreen
import com.anonymous.weatherforecast.screens.SearchScreen
import com.anonymous.weatherforecast.screens.favorites.FavoriteViewModel
import com.anonymous.weatherforecast.screens.favorites.FavouriteScreen
import com.anonymous.weatherforecast.screens.main.MainScreen
import com.anonymous.weatherforecast.screens.main.WeatherViewModel
import com.anonymous.weatherforecast.screens.settings.SettingsScreen
import com.anonymous.weatherforecast.screens.settings.SettingsViewModel
import com.anonymous.weatherforecast.screens.splash.SplashScreen

@Composable
fun WeatherForecastNavigation() {
    val navController = rememberNavController()
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(
            route = WeatherScreens.MainScreen.name + "/{city}", arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )) { navBack ->
            val city = navBack.arguments?.getString("city")
            if (city != null) {
                val viewModel: WeatherViewModel = hiltViewModel()
                MainScreen(
                    navController = navController,
                    mainViewModel = viewModel,
                    settingsViewModel = settingsViewModel,
                    city = city,
                    favoriteViewModel = favoriteViewModel
                )
            }
        }
        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController, favoriteViewModel)
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController, settingsViewModel)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
    }
}