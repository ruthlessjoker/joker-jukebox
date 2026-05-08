package com.joker.jukebox.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.joker.jukebox.data.Track
import com.joker.jukebox.service.JukeboxMediaLibraryService
import com.joker.jukebox.service.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JukeboxViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JukeboxUiState())
    val uiState: StateFlow<JukeboxUiState> = _uiState.asStateFlow()

    private var mediaController: MediaController? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null

    fun connectToService(context: Context) {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, JukeboxMediaLibraryService::class.java)
        )
        
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        
        controllerFuture?.addListener({
            try {
                mediaController = controllerFuture?.get()
                // Load tracks once connected
                viewModelScope.launch {
                    val tracks = mediaRepository.getAllTracks()
                    _uiState.value = JukeboxUiState(tracks = tracks)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }, MoreExecutors.directExecutor())
    }
    
    fun playTrack(trackId: String) {
        viewModelScope.launch {
            val track = mediaRepository.getTrackById(trackId)
            if (track != null) {
                mediaController?.let { controller ->
                    controller.clearMediaItems()
                    val mediaItem = MediaItem.Builder()
                        .setMediaId(track.id)
                        .setUri(track.mediaUri)
                        .build()
                    controller.addMediaItem(mediaItem)
                    controller.prepare()
                    controller.play()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaController?.release()
        controllerFuture?.let {
            if (!it.isCancelled && !it.isDone) it.cancel(true)
        }
    }
}

data class JukeboxUiState(
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = false
)