package com.practicum.playlist_maker_android_rodionovmatvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.practicum.playlist_maker_android_rodionovmatvey.ui.theme.PlaylistmakerandroidRodionovMatveyTheme

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistmakerandroidRodionovMatveyTheme {
                SettingsScreen(
                    onBackClick = { finish() }
                )
            }
        }
    }
}