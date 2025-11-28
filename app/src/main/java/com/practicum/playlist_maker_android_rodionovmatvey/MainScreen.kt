package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPlaylistsClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(Color(0xFF3772E7)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Playlist maker",
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-24).dp)
                .background(Color.White)
                .padding(top = 24.dp)
        ) {
            MenuRow(
                title = "Поиск",
                iconStart = R.drawable.ic_search,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onSearchClick
            )
            MenuRow(
                title = "Плейлисты",
                iconStart = R.drawable.ic_playlists,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onPlaylistsClick
            )
            MenuRow(
                title = "Избранное",
                iconStart = R.drawable.ic_favorites,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onFavoritesClick
            )
            MenuRow(
                title = "Настройки",
                iconStart = R.drawable.ic_settings,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun MenuRow(
    title: String,
    iconStart: Int,
    iconEnd: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconStart),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            fontSize = 22.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
        )

        Image(
            painter = painterResource(id = iconEnd),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}