package com.joker.jukebox.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.jukebox.data.Track
import com.joker.jukebox.ui.theme.JokerTheme
import com.joker.jukebox.ui.theme.NeonGreen
import com.joker.jukebox.ui.viewmodel.JukeboxViewModel

@Composable
fun JokerJukeboxScreen(
    viewModel: JukeboxViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Connect to the music service when the screen starts
    LaunchedEffect(Unit) {
        viewModel.connectToService(context)
    }

    JokerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // The Flickering Header Title
                Text(
                    text = "JOKEBOX",
                    color = NeonGreen,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
                
                if (uiState.tracks.isEmpty()) {
                    Text("Loading tracks from the asylum...", color = NeonGreen)
                } else {
                    LazyColumn {
                        items(uiState.tracks) { track ->
                            TrackItem(track = track, onPlay = { viewModel.playTrack(track.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackItem(track: Track, onPlay: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = track.title, color = NeonGreen)
                Text(text = track.artist, style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onPlay) {
                Text("Play")
            }
        }
    }
}