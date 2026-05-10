package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .background(Color(0xFF3772E7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(64.dp)
                .background(Color(0xFF3772E7))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.main_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(top = 24.dp)
        ) {
            MenuRow(
                title = stringResource(R.string.screen_search),
                iconStart = R.drawable.ic_search,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onSearchClick
            )

            MenuRow(
                title = stringResource(R.string.screen_playlists),
                iconStart = R.drawable.ic_playlists,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onPlaylistsClick
            )

            MenuRow(
                title = stringResource(R.string.screen_favorites_short),
                iconStart = R.drawable.ic_favorites,
                iconEnd = R.drawable.ic_chevron_right,
                onClick = onFavoritesClick
            )

            MenuRow(
                title = stringResource(R.string.settings_title),
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
            .height(72.dp)
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconStart),
            contentDescription = null,
            modifier = Modifier.padding(end = 24.dp)
        )

        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(id = iconEnd),
            contentDescription = null
        )
    }
}