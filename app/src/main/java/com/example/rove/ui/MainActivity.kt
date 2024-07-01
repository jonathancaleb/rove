package com.example.rove.ui

import androidx.activity.viewModels

import com.example.rove.BaseActivity
import com.example.rove.RovePreferences
import com.example.rove.databinding.MainActivityBinding
import com.example.rove.databinding.PlayerControlsPanelBinding

class MainActivity : BaseActivity(), UIControlInterface, MediaControlInterface {
    // View binding classes
    private lateinit var mMainActivityBinding: MainActivityBinding
    private lateinit var mPlayerControlsPanelBinding: PlayerControlsPanelBinding

    // View model
    private val mMusicViewModel: MusicViewModel by viewModels()

    // Preferences
    private val mRovePreferences get() = RovePreferences.getPrefsInstance()

    // Fragments
    private var mArtistsFragment: MusicContainersFragment? = null
    private var mAllMusicFragment: AllMusicFragment? = null
    private var mFoldersFragment: MusicContainersFragment? = null
    private var mAlbumsFragment: MusicContainersFragment? = null
    private var mSettingsFragment: SettingsFragment? = null
    private var mDetailsFragment: DetailsFragment? = null
}