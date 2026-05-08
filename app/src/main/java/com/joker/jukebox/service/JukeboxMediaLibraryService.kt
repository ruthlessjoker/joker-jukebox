package com.joker.jukebox.service

import android.media.AudioAttributes
import androidx.media3.common.AudioAttributes as Media3AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaLibraryService
import com.google.common.util.concurrent.Futures
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class JukeboxMediaLibraryService : MediaLibraryService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    @Inject
    lateinit var mediaRepository: MediaRepository
    
    private var mediaSession: MediaSession? = null
    private var player: ExoPlayer? = null
    
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    
    override fun onCreate() {
        super.onCreate()
        initializePlayer()
        initializeSession()
    }
    
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .setAudioAttributes(
                Media3AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
            .setHandleAudioBecomingNoisy(true)
        override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? {
        // TODO: Re-enable MediaLibrarySession when API is stable
        // val sessionCallback = JukeboxMediaLibrarySessionCallback(mediaRepository)
        // return MediaLibrarySession.Builder(this, player, sessionCallback)
        //     .setSessionActivity(sessionActivityPendingIntent)
        //     .build()
        
        // Return null for now to allow basic build
        return null
    }
    }
}

data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val currentMediaItem: MediaItem? = null
)
