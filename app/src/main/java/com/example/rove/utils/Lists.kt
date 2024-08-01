package com.example.rove.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rove.RoveConstants
import com.example.rove.RovePreferences
import com.example.rove.R
import com.example.rove.extensions.findSorting
import com.example.rove.extensions.toFormattedDuration
import com.example.rove.models.Music
import com.example.rove.models.Sorting
import com.example.rove.ui.UIControlInterface
import java.util.*

@SuppressLint("DefaultLocale")
object Lists {

    @JvmStatic
    fun processQueryForStringsLists(query: String?, list: List<String>?): List<String>? {
        // In real app you'd have it instantiated just once
        val filteredStrings = mutableListOf<String>()

        return try {
            // Case insensitive search
            list?.iterator()?.let { iterate ->
                while (iterate.hasNext()) {
                    val filteredString = iterate.next()
                    if (filteredString.lowercase().contains(query?.lowercase()!!)) {
                        filteredStrings.add(filteredString)
                    }
                }
            }
            return filteredStrings
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun processQueryForMusic(query: String?, musicList: List<Music>?): List<Music>? {
        // In real app you'd have it instantiated just once
        val filteredSongs = mutableListOf<Music>()
        val isShowDisplayName =
            RovePreferences.getPrefsInstance().songsVisualization== RoveConstants.FN
        return try {
            // Case insensitive search
            musicList?.iterator()?.let { iterate ->
                while (iterate.hasNext()) {
                    val filteredSong = iterate.next()
                    val toFilter = if (isShowDisplayName) {
                        filteredSong.displayName
                    } else {
                        filteredSong.title
                    }
                    if (toFilter?.lowercase()!!.contains(query?.lowercase()!!)) {
                        filteredSongs.add(filteredSong)
                    }
                }
            }
            return filteredSongs
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun getSortedList(id: Int, list: MutableList<String>?) = when (id) {
        RoveConstants.ASCENDING_SORTING -> list?.apply {
            Collections.sort(this, String.CASE_INSENSITIVE_ORDER)
        }
        RoveConstants.DESCENDING_SORTING -> list?.apply {
            Collections.sort(this, String.CASE_INSENSITIVE_ORDER)
        }?.asReversed()
        else -> list
    }

    @JvmStatic
    fun getSortedListWithNull(id: Int, list: MutableList<String?>?): MutableList<String>? {
        val withoutNulls = list?.map {
            transformNullToEmpty(it)
        }?.toMutableList()
        return getSortedList(id, withoutNulls)
    }

    private fun transformNullToEmpty(toTrans: String?): String {
        if (toTrans == null) return ""
        return toTrans
    }

    @JvmStatic
    fun getSelectedSorting(sorting: Int, menu: Menu): MenuItem {
        return when (sorting) {
            RoveConstants.ASCENDING_SORTING -> menu.findItem(R.id.ascending_sorting)
            RoveConstants.DESCENDING_SORTING -> menu.findItem(R.id.descending_sorting)
            else -> menu.findItem(R.id.default_sorting)
        }
    }

    @JvmStatic
    fun getSelectedSortingForMusic(sorting: Int, menu: Menu): MenuItem {
        return when (sorting) {
            RoveConstants.ASCENDING_SORTING -> menu.findItem(R.id.ascending_sorting)
            RoveConstants.DESCENDING_SORTING -> menu.findItem(R.id.descending_sorting)
            RoveConstants.DATE_ADDED_SORTING -> menu.findItem(R.id.date_added_sorting)
            RoveConstants.DATE_ADDED_SORTING_INV -> menu.findItem(R.id.date_added_sorting_inv)
            RoveConstants.ARTIST_SORTING -> menu.findItem(R.id.artist_sorting)
            RoveConstants.ARTIST_SORTING_INV -> menu.findItem(R.id.artist_sorting_inv)
            RoveConstants.ALBUM_SORTING -> menu.findItem(R.id.album_sorting)
            RoveConstants.ALBUM_SORTING_INV -> menu.findItem(R.id.album_sorting_inv)
            else -> menu.findItem(R.id.default_sorting)
        }
    }

    @JvmStatic
    fun getSortedMusicList(id: Int, list: List<Music>?): List<Music>? {
        return when (id) {
            RoveConstants.ASCENDING_SORTING -> getSortedListBySelectedVisualization(list)
            RoveConstants.DESCENDING_SORTING -> getSortedListBySelectedVisualization(list)?.asReversed()
            RoveConstants.TRACK_SORTING -> list?.sortedBy { it.track }
            RoveConstants.TRACK_SORTING_INVERTED -> list?.sortedBy { it.track }?.asReversed()
            else -> list
        }
    }

    @JvmStatic
    fun getSortedMusicListForFolder(id: Int, list: List<Music>?): List<Music>? {
        return when (id) {
            RoveConstants.ASCENDING_SORTING -> list?.sortedBy { it.displayName }
            RoveConstants.DESCENDING_SORTING -> list?.sortedBy { it.displayName }?.asReversed()
            RoveConstants.DATE_ADDED_SORTING -> list?.sortedBy { it.dateAdded }?.asReversed()
            RoveConstants.DATE_ADDED_SORTING_INV -> list?.sortedBy { it.dateAdded }
            RoveConstants.ARTIST_SORTING -> list?.sortedBy { it.artist }
            RoveConstants.ARTIST_SORTING_INV -> list?.sortedBy { it.artist }?.asReversed()
            else -> list
        }
    }

    @JvmStatic
    fun getSortedMusicListForAllMusic(id: Int, list: List<Music>?): List<Music>? {
        return when (id) {
            RoveConstants.ASCENDING_SORTING -> getSortedListBySelectedVisualization(list)
            RoveConstants.DESCENDING_SORTING -> getSortedListBySelectedVisualization(list)?.asReversed()
            RoveConstants.TRACK_SORTING -> list?.sortedBy { it.track }
            RoveConstants.TRACK_SORTING_INVERTED -> list?.sortedBy { it.track }?.asReversed()
            RoveConstants.DATE_ADDED_SORTING -> list?.sortedBy { it.dateAdded }?.asReversed()
            RoveConstants.DATE_ADDED_SORTING_INV -> list?.sortedBy { it.dateAdded }
            RoveConstants.ARTIST_SORTING -> list?.sortedBy { it.artist }
            RoveConstants.ARTIST_SORTING_INV -> list?.sortedBy { it.artist }?.asReversed()
            RoveConstants.ALBUM_SORTING -> list?.sortedBy { it.album }
            RoveConstants.ALBUM_SORTING_INV -> list?.sortedBy { it.album }?.asReversed()
            else -> list
        }
    }

    private fun getSortedListBySelectedVisualization(list: List<Music>?) = list?.sortedBy {
        if (RovePreferences.getPrefsInstance().songsVisualization == RoveConstants.FN) {
            it.displayName
        } else {
            it.title
        }
    }

    @JvmStatic
    fun getSongsSorting(currentSorting: Int) = when (currentSorting) {
        RoveConstants.TRACK_SORTING -> RoveConstants.TRACK_SORTING_INVERTED
        RoveConstants.TRACK_SORTING_INVERTED -> RoveConstants.ASCENDING_SORTING
        RoveConstants.ASCENDING_SORTING -> RoveConstants.DESCENDING_SORTING
        else -> RoveConstants.TRACK_SORTING
    }

    @JvmStatic
    fun getSongsDisplayNameSorting(currentSorting: Int): Int {
        if (currentSorting == RoveConstants.ASCENDING_SORTING) {
            return RoveConstants.DESCENDING_SORTING
        }
        return RoveConstants.ASCENDING_SORTING
    }

    fun hideItems(items: List<String>) {
        val hiddenArtistsFolders = RovePreferences.getPrefsInstance().filters?.toMutableList()
        hiddenArtistsFolders?.addAll(items)
        RovePreferences.getPrefsInstance().filters = hiddenArtistsFolders?.toSet()
    }

    @JvmStatic
    fun addToFavorites(
        context: Context,
        song: Music?,
        canRemove: Boolean,
        playerPosition: Int,
        launchedBy: String
    ) {
        val favorites = RovePreferences.getPrefsInstance().favorites?.toMutableList() ?: mutableListOf()
        song?.copy(startFrom = playerPosition, launchedBy = launchedBy)?.let { savedSong ->
            if (!favorites.contains(savedSong)) {
                favorites.add(savedSong)

                var msg = context.getString(
                    R.string.favorite_added,
                    savedSong.title,
                    playerPosition.toLong().toFormattedDuration(
                        isAlbum = false,
                        isSeekBar = false
                    )
                )
                if (playerPosition == 0) {
                    msg = msg.replace(context.getString(R.string.favorites_no_position), "")
                }

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            } else if (canRemove) {
                favorites.remove(savedSong)
            }
            RovePreferences.getPrefsInstance().favorites = favorites
        }
    }

    @JvmStatic
    fun getDefSortingMode() = if (RovePreferences.getPrefsInstance().songsVisualization == RoveConstants.FN) {
        RoveConstants.ASCENDING_SORTING
    } else {
        RoveConstants.TRACK_SORTING
    }

    @JvmStatic
    fun getUserSorting(launchedBy: String) = RovePreferences.PREFS_DETAILS_SORTING.findSorting(launchedBy)

    @JvmStatic
    fun addToSortings(
        activity: Activity,
        artistOrFolder: String?,
        launchedBy: String,
        sorting: Int
    ) {
        val prefs = RovePreferences.getPrefsInstance()
        val toSorting = Sorting(artistOrFolder, launchedBy, prefs.songsVisualization, sorting)
        val sortings = prefs.sortings?.toMutableList() ?: mutableListOf()
        artistOrFolder?.findSorting(launchedBy)?.let { toReplaceSorting ->
            sortings.remove(toReplaceSorting)
            val newSorting = toReplaceSorting.copy(sorting = sorting)
            sortings.add(newSorting)
            prefs.sortings = sortings
            return
        }
        if (!sortings.contains(toSorting)) {
            sortings.add(toSorting)
        }
        prefs.sortings = sortings
        (activity as UIControlInterface).onUpdateSortings()
    }
}
