package com.joker.jukebox.service

import android.net.Uri
import com.joker.jukebox.data.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepository @Inject constructor() {
    
    // Placeholder tracks until we fully implement Plex
    val tracks = listOf(
        Track(
            id = "1",
            title = "Why So Serious?",
            artist = "Joker's Symphony",
            album = "Arkham Nights",
            duration = 245000L,
            artworkUri = Uri.EMPTY,
            mediaUri = Uri.EMPTY
        ),
        Track(
            id = "2",
            title = "Purple Haze",
            artist = "The Jesters",
            album = "Chaos Theory",
            duration = 198000L,
            artworkUri = Uri.EMPTY,
            mediaUri = Uri.EMPTY
        )
    )
    
    fun getAllTracks(): List<Track> = tracks
    
    fun getTrackById(id: String): Track? = tracks.find { it.id == id }
}