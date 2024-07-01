package com.example.rove.ui

import com.example.rove.models.Music

/**
 * Interface defining media control operations for the music player.
 */
interface MediaControlInterface {

    /**
     * Called when a song is selected.
     *
     * @param song The selected song. Can be null if no song is selected.
     * @param songs The list of songs available. Can be null if no songs are available.
     * @param songLaunchedBy The entity that launched the song.
     */
    fun onSongSelected(song: Music?, songs: List<Music>?, songLaunchedBy: String)

    /**
     * Called when songs are shuffled.
     *
     * @param songs The list of shuffled songs. Can be null if no songs are available.
     * @param songLaunchedBy The entity that initiated the shuffle.
     */
    fun onSongsShuffled(songs: List<Music>?, songLaunchedBy: String)

    /**
     * Called when a song is added to the queue.
     *
     * @param song The song to add to the queue. Can be null if no song is provided.
     */
    fun onAddToQueue(song: Music?)

    /**
     * Called when an album is added to the queue.
     *
     * @param songs The list of songs in the album. Can be null if no songs are provided.
     * @param forcePlay A pair where the first value indicates whether to force play the album,
     * and the second value is the song to restore after force play (can be null).
     */
    fun onAddAlbumToQueue(songs: List<Music>?, forcePlay: Pair<Boolean, Music?>)

    /**
     * Called to update the list of songs in the currently playing album.
     *
     * @param songs The updated list of songs in the album. Can be null if no songs are provided.
     */
    fun onUpdatePlayingAlbumSongs(songs: List<Music>?)

    /**
     * Called when the playback speed is toggled.
     */
    fun onPlaybackSpeedToggled()

    /**
     * Called to handle updates to the cover options.
     */
    fun onHandleCoverOptionsUpdate()

    /**
     * Called to update the playback position from the Now Playing screen.
     *
     * @param position The new playback position in milliseconds.
     */
    fun onUpdatePositionFromNP(position: Int)
}
