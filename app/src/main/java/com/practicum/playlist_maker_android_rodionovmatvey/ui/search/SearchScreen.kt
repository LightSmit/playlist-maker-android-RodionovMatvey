package com.practicum.playlist_maker_android_rodionovmatvey.ui.search

import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.playlist_maker_android_rodionovmatvey.R

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    var text by remember { mutableStateOf("") }
    var isSearchFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back)
                )
            }

            Text(
                text = stringResource(R.string.screen_search),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                viewModel.onSearchTextChanged(newText)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_placeholder)
                )
            },
            trailingIcon = {
                if (text.isNotBlank()) {
                    IconButton(
                        onClick = {
                            text = ""
                            viewModel.clear()
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.action_clear_search)
                        )
                    }
                }
            },
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isSearchFocused = focusState.isFocused
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (screenState) {
            is SearchState.Initial -> {
                if (isSearchFocused && text.isBlank() && searchHistory.isNotEmpty()) {
                    SearchHistoryBlock(
                        history = searchHistory,
                        onHistoryClick = { query ->
                            text = query
                            viewModel.onSearchTextChanged(query)
                        }
                    )
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Empty -> {
                SearchPlaceholder(
                    imageRes = R.drawable.ic_search_empty,
                    title = stringResource(R.string.search_nothing_found),
                    subtitle = null,
                    buttonText = null,
                    onButtonClick = null
                )
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).tracks

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tracks) { track ->
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track.id) }
                        )
                    }
                }
            }

            is SearchState.Fail -> {
                SearchPlaceholder(
                    imageRes = R.drawable.ic_search_error,
                    title = stringResource(R.string.search_connection_problem),
                    subtitle = stringResource(R.string.search_loading_failed),
                    buttonText = stringResource(R.string.action_refresh),
                    onButtonClick = { viewModel.retrySearch() }
                )
            }
        }
    }
}

@Composable
private fun SearchHistoryBlock(
    history: List<String>,
    onHistoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.search_history_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFECECF0),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        ) {
            history.forEach { query ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onHistoryClick(query) }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = query,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchPlaceholder(
    imageRes: Int,
    title: String,
    subtitle: String?,
    buttonText: String?,
    onButtonClick: (() -> Unit)?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 120.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = subtitle,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            if (buttonText != null && onButtonClick != null) {
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onButtonClick) {
                    Text(buttonText)
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit
) {
    val viewModel: SearchViewModel =
        viewModel(factory = SearchViewModel.getViewModelFactory())

    SearchScreen(
        modifier = modifier,
        viewModel = viewModel,
        onBackClick = onBackClick,
        onTrackClick = onTrackClick
    )
}