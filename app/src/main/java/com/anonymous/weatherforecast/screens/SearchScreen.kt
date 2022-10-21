package com.anonymous.weatherforecast.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anonymous.weatherforecast.widgets.CreateAppTopBar

@Composable
fun SearchScreen(navController: NavHostController) {
    CreateAppTopBar(navController = navController, title = "Search", isMainScreen = false) {
        SearchBar {
            navController.previousBackStackEntry?.arguments?.putString(
                "city",
                it
            )
            navController.popBackStack()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(onSearch: (String) -> Unit) {
    val searchQuery = remember {
        mutableStateOf("")
    }
    val isSearchQueryValid = remember(searchQuery.value) {
        val symbols = "0123456789/?!:;%"
        searchQuery.value.isNotBlank() && !searchQuery.value.any { it in symbols }
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CommonTextField(
            state = searchQuery,
            label = "Search",
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            isError = isError,
            onAction = KeyboardActions {
                if (!isSearchQueryValid) {
                    isError.value = true
                    return@KeyboardActions
                }
                onSearch(searchQuery.value)
                searchQuery.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(
    state: MutableState<String>,
    label: String,
    onAction: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
    isError: MutableState<Boolean>,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            isError.value = false
            state.value = it
        },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        label = {
            Text(text = label)
        },
        isError = isError.value,
        maxLines = 1,
        singleLine = true,
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Black,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Black,
            errorLabelColor = Color.Red
        )
    )
}