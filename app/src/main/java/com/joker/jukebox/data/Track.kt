package com.joker.jukebox.data

import android.net.Uri

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val artworkUri: Uri,
    val mediaUri: Uri
)