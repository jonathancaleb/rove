package com.example.rove

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

/**
 * Custom application class for the Rove app.
 *
 * This class initializes necessary components when the application starts,
 * such as preferences and image loading.
 */
class RoveApp : Application(), ImageLoaderFactory {

    /**
     * Called when the application is starting. Initializes preferences.
     */
    override fun onCreate() {
        super.onCreate()

        // Initialize application preferences
        RovePreferences.initPrefs(applicationContext)
    }

    /**
     * Creates a new instance of [ImageLoader] for image loading.
     *
     * @return A new [ImageLoader] instance.
     */
    override fun newImageLoader(): ImageLoader {
        // TODO: Implement image loader configuration
        // Currently not implemented
        TODO("Not yet implemented")
    }
}
