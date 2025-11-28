package com.practicum.playlist_maker_android_rodionovmatvey.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.practicum.playlist_maker_android_rodionovmatvey.R

val YandexSans = FontFamily(
    Font(R.font.yandex_sans_display_bold)
)

val Typography = Typography(
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = YandexSans
    ),
    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = YandexSans
    ),
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = YandexSans
    )
)
