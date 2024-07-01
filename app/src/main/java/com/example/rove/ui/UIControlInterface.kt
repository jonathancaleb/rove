package com.example.rove.ui

/**
 * UIControlInterface
 *
 * This interface defines a set of methods for controlling various UI actions and updates
 * within the application. Implementing classes must provide concrete implementations
 * for all these methods.
 */
interface UIControlInterface {

    /**
     * Called when the appearance of the app changes, such as a theme change.
     *
     * @param isThemeChanged Boolean indicating whether the theme has changed.
     */
    fun onAppearanceChanged(isThemeChanged: Boolean)

    /**
     * Called to open a new details fragment.
     */
    fun onOpenNewDetailsFragment()

    /**
     * Called when an artist or folder is selected.
     *
     * @param artistOrFolder String representing the selected artist or folder.
     * @param launchedBy String indicating what triggered the selection.
     */
    fun onArtistOrFolderSelected(artistOrFolder: String, launchedBy: String)

    /**
     * Called when the favorites list is updated.
     *
     * @param clear Boolean indicating whether the favorites list should be cleared.
     */
    fun onFavoritesUpdated(clear: Boolean)

    /**
     * Called when a favorite item is added or removed.
     */
    fun onFavoriteAddedOrRemoved()

    /**
     * Called to close the current activity.
     */
    fun onCloseActivity()

    /**
     * Called to add items to the filter.
     *
     * @param stringsToFilter List of strings representing the items to be added to the filter.
     */
    fun onAddToFilter(stringsToFilter: List<String>?)

    /**
     * Called to clear all filters.
     */
    fun onFiltersCleared()

    /**
     * Called when a permission is denied.
     */
    fun onDenyPermission()

    /**
     * Called to open the album of the currently playing artist.
     */
    fun onOpenPlayingArtistAlbum()

    /**
     * Called to open the equalizer settings.
     */
    fun onOpenEqualizer()

    /**
     * Called to open the sleep timer dialog.
     */
    fun onOpenSleepTimerDialog()

    /**
     * Called to enable the equalizer.
     */
    fun onEnableEqualizer()

    /**
     * Called to update the sorting order of items.
     */
    fun onUpdateSortings()
}
