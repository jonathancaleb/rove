package com.example.rove.models

import com.squareup.moshi.JsonClass

/**
 * Data class representing a Music object.
 *
 * @property artist The artist of the music track. Nullable
 * @property year The year the music track was released.
 * @property track The track number of the music track on the album.
 * @property title The title of the music track.
 * @property displayName The display name of the music track, usually the title.
 * @property duration The duration of the music track in milliseconds.
 * @property album The album name the music track belongs to.
 * @property albumId The ID of the album.
 * @property relativePath The relative path to the music file.
 * @property id The unique ID of the music track.
 * @property launchedBy The application or user that launched the music track.
 * @property startFrom The position in milliseconds to start playing the track from.
 * @property dateAdded The date the music track was added, represented as an integer (usually a Unix timestamp).
 */
@JsonClass(generateAdapter = true)
data class Music(
    val artist: String?,        // The artist of the music track. Nullable.
    val year: Int,              // The year the music track was released.
    val track: Int,             // The track number of the music track on the album.
    val title: String?,         // The title of the music track. Nullable.
    val displayName: String?,   // The display name of the music track. Nullable.
    val duration: Long,         // The duration of the music track in milliseconds.
    val album: String?,         // The album name the music track belongs to. Nullable.
    val albumId: Long?,         // The ID of the album. Nullable.
    val relativePath: String?,  // The relative path to the music file. Nullable.
    val id: Long?,              // The unique ID of the music track. Nullable.
    val launchedBy: String,     // The application or user that launched the music track.
    val startFrom: Int,         // The position in milliseconds to start playing the track from.
    val dateAdded: Int          // The date the music track was added, represented as an integer (usually a Unix timestamp).
)
