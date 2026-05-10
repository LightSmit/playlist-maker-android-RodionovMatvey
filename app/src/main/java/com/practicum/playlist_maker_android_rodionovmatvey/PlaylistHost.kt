package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practicum.playlist_maker_android_rodionovmatvey.ui.search.SearchScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) },
                onPlaylistsClick = { navController.navigate(Screen.PLAYLISTS.route) },
                onFavoritesClick = { navController.navigate(Screen.FAVORITES.route) }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { trackId ->
                    navController.navigate(Screen.trackDetailsRoute(trackId))
                }
            )
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                addNewPlaylist = {
                    navController.navigate(Screen.CREATE_PLAYLIST.route)
                },
                navigateToPlaylist = { playlistId ->
                    navController.navigate(Screen.playlistDetailsRoute(playlistId))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FAVORITES.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { trackId ->
                    navController.navigate(Screen.trackDetailsRoute(trackId))
                }
            )
        }

        composable(Screen.CREATE_PLAYLIST.route) {
            CreatePlaylistScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TRACK_DETAILS.route,
            arguments = listOf(navArgument("trackId") { type = NavType.LongType })
        ) { entry ->
            val trackId = entry.arguments?.getLong("trackId") ?: return@composable
            TrackDetailsScreen(
                trackId = trackId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.PLAYLIST_DETAILS.route,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { entry ->
            val playlistId = entry.arguments?.getLong("playlistId") ?: return@composable

            PlaylistDetailsScreen(
                playlistId = playlistId,
                onBackClick = { navController.popBackStack() },
                navigateToTrack = { trackId ->
                    navController.navigate(Screen.trackDetailsRoute(trackId))
                }
            )
        }
    }
}