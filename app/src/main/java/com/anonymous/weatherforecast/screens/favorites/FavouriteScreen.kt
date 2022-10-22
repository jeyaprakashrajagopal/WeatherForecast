package com.anonymous.weatherforecast.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.model.Favorite
import com.anonymous.weatherforecast.screens.favorites.FavoriteViewModel
import com.anonymous.weatherforecast.widgets.WeatherAppToolBar

@Composable
fun FavouriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoritesList = favoriteViewModel.favoritesList.collectAsState()
    WeatherAppToolBar(navController = navController, title = "Favorites", isMainScreen = false) {
        if (favoritesList.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Your favorites list is empty!")
            }
        }
        LazyColumn {
            items(favoritesList.value) {
                CreateFavoriteRow(favoriteViewModel, it)
            }
        }
    }
}

@Composable
fun CreateFavoriteRow(favoriteViewModel: FavoriteViewModel, favorite: Favorite) {
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.LightGray.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, style = MaterialTheme.typography.h5)
            Text(
                text = favorite.country, modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete icon",
                tint = Color.Red.copy(alpha = 0.5f),
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite)
                }
            )
        }
    }
}