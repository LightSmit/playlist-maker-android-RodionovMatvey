package com.practicum.playlist_maker_android_rodionovmatvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.practicum.playlist_maker_android_rodionovmatvey.ui.theme.PlaylistmakerandroidRodionovMatveyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Creator.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerandroidRodionovMatveyTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}