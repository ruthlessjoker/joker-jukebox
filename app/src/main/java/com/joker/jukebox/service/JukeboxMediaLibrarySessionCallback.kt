package com.joker.jukebox.service

import androidx.media3.session.MediaLibrarySession
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import androidx.media3.session.MediaLibraryService.LibraryParams
import androidx.media3.common.MediaItem

class JukeboxMediaLibrarySessionCallback(
    private val mediaRepository: MediaRepository
) : MediaLibrarySession.Callback {

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        return MediaSession.ConnectionResult.accept(session.availableSessionCommands, session.availablePlayerCommands)
    }

    override fun onGetLibraryRoot(
        session: MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        params: LibraryParams?
    ): ListenableFuture<MediaItem> {
        return Futures.immediateFuture(
            MediaItem.Builder()
                .setMediaId("root")
                .setIsPlayable(false)
                .build()
        )
    }

    override fun onGetChildren(
        session: MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        parentId: String,
        page: Int,
        pageSize: Int,
        params: LibraryParams?
    ): ListenableFuture<ImmutableList<MediaItem>> {
        val items = mediaRepository.getAllTracks().map { track ->
            MediaItem.Builder()
                .setMediaId(track.id)
                .setUri(track.mediaUri)
                .build()
        }
        return Futures.immediateFuture(ImmutableList.copyOf(items))
    }
}