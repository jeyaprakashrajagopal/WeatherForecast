package com.anonymous.weatherforecast.screens.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.R
import com.anonymous.weatherforecast.model.Favorite
import com.anonymous.weatherforecast.navigation.WeatherScreens
import com.anonymous.weatherforecast.screens.favorites.FavoriteViewModel

@Composable
fun CreateTopAppBar(
    title: String,
    isMainScreen: Boolean = true,
    elevation: Dp,
    navController: NavHostController,
    hiltViewModel: FavoriteViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog, navController)
    }

    val favoritesList by hiltViewModel.favoritesList.collectAsState()
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More icon")
                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            if (isMainScreen) {
                val dataList = title.split(",")
                val favorite = favoritesList.filter { it.city == dataList[0] }.firstOrNull()

                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite icon",
                    tint = if (favorite != null) Color.Red.copy(alpha = 0.6f) else Color.LightGray,
                    modifier = Modifier
                        .padding(5.dp)

                        .clickable {
                            if (favorite == null) {
                                hiltViewModel.insertFavorite(
                                    Favorite(
                                        city = dataList[0],
                                        country = dataList[1]
                                    )
                                )
                                showToast(
                                    context,
                                    context.getString(
                                        R.string.add_favorite_toast_label,
                                        dataList[0]
                                    )
                                )
                            } else {
                                hiltViewModel.deleteFavorite(favorite = favorite)
                                showToast(
                                    context,
                                    context.getString(
                                        R.string.remove_favorite_toast_label,
                                        dataList[0]
                                    )
                                )
                            }
                        }
                )
            } else {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back arrow")
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

fun showToast(context: Context, toastMessage: String) {
    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
}

@Composable
private fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavHostController
) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val dropDownMenuItems = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded, onDismissRequest = {
                showDialog.value = false
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            dropDownMenuItems.forEach {
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                    when (it) {
                        "About" -> navController.navigate(WeatherScreens.AboutScreen.name)
                        "Favorites" -> navController.navigate(WeatherScreens.FavouriteScreen.name)
                        "Settings" -> navController.navigate(WeatherScreens.SettingsScreen.name)
                    }
                }) {
                    Icon(
                        imageVector = when (it) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            "Settings" -> Icons.Default.Settings
                            else -> Icons.Default.Warning
                        }, contentDescription = null
                    )
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}
