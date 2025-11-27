package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
                onPlaylistsClick = { /* потом добавишь */ },
                onFavoritesClick = { /* потом добавишь */ }
            )
        }

        composable(Screen.SEARCH.route) {
            // Экран поиска (у тебя уже есть SearchScreen)
            SearchScreen()
        }

        composable(Screen.SETTINGS.route) {
            // Экран настроек (у тебя уже есть SettingsScreen)
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
