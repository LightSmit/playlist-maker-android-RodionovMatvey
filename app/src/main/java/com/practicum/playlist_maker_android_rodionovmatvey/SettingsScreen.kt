package com.practicum.playlist_maker_android_rodionovmatvey

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    val title = stringResource(R.string.settings_title)
    val darkThemeTitle = stringResource(R.string.dark_theme)
    val shareTitle = stringResource(R.string.share_app)
    val writeTitle = stringResource(R.string.write_to_support)
    val userAgreementTitle = stringResource(R.string.user_agreement)

    val shareMessage = stringResource(R.string.share_message)

    val emailAddress = stringResource(R.string.support_email_address)
    val emailSubject = stringResource(R.string.support_email_subject)
    val emailBody = stringResource(R.string.support_email_body)

    val offerUrl = stringResource(R.string.offer_url)

    var isDarkThemeEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrowback),
                    contentDescription = "Назад"
                )
            }
            Text(
                text = title,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.yandex_sans_display_bold)),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = darkThemeTitle,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkThemeEnabled,
                onCheckedChange = { isDarkThemeEnabled = it }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareMessage)
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = shareTitle,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = "Поделиться"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    val uri = Uri.parse("mailto:$emailAddress")
                    val emailIntent = Intent(Intent.ACTION_SENDTO, uri).apply {
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_TEXT, emailBody)
                    }
                    context.startActivity(emailIntent)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = writeTitle,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_support),
                contentDescription = "Написать в поддержку"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(offerUrl)
                    )
                    context.startActivity(browserIntent)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userAgreementTitle,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Пользовательское соглашение"
            )
        }
    }
}