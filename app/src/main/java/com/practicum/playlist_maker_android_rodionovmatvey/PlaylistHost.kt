package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.playlist_maker_android_rodionovmatvey.ui.search.SearchScreen

@Composable
fun PlaylistHost(navController: NavHostController) {

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navigateTo(Screen.SEARCH) },
                onSettingsClick = { navigateTo(Screen.SETTINGS) },
                onPlaylistsClick = { /* TODO */ },
                onFavoritesClick = { /* TODO */ }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen()
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}