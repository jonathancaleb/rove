package com.example.rove

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import com.example.rove.utils.Theming


class GoApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        RovePreferences.initPrefs(applicationContext)
        AppCompatDelegate.setDefaultNightMode(Theming.getDefaultNightMode(applicationContext))
    }

    override fun newImageLoader() = ImageLoader.Builder(this)
        .diskCachePolicy(CachePolicy.DISABLED)
        .crossfade(true)
        .build()
}
