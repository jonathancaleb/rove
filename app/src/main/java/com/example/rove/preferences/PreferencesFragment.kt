package com.example.rove.preferences

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.rove.RovePreferences
import com.example.rove.R
import com.example.rove.dialogs.Dialogs
import com.example.rove.dialogs.RecyclerSheet
import com.example.rove.player.MediaPlayerHolder
import com.example.rove.ui.MediaControlInterface
import com.example.rove.ui.UIControlInterface
import com.example.rove.utils.Theming


class PreferencesFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private lateinit var mUIControlInterface: UIControlInterface
    private lateinit var mMediaControlInterface: MediaControlInterface

    private val mMediaPlayerHolder get() = MediaPlayerHolder.getInstance()
    private val mRovePreferences get() = RovePreferences.getPrefsInstance()

    override fun setDivider(divider: Drawable?) {
        super.setDivider(null)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mUIControlInterface = activity as UIControlInterface
            mMediaControlInterface = activity as MediaControlInterface
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<Preference>(getString(R.string.theme_pref))?.icon = ContextCompat.getDrawable(requireContext(), if (Theming.isThemeNight(resources)) {
            R.drawable.ic_night
        } else {
            R.drawable.ic_day
        })

        findPreference<Preference>(getString(R.string.theme_pref_black))?.isVisible = Theming.isThemeNight(resources)

        findPreference<Preference>(getString(R.string.accent_pref))?.run {
            summary = Theming.getAccentName(resources, mRovePreferences.accent)
            onPreferenceClickListener = this@PreferencesFragment
        }

        findPreference<Preference>(getString(R.string.filter_pref))?.onPreferenceClickListener = this@PreferencesFragment

        findPreference<Preference>(getString(R.string.active_tabs_pref))?.run {
            summary = mRovePreferences.activeTabs.size.toString()
            onPreferenceClickListener = this@PreferencesFragment
        }

        findPreference<Preference>(getString(R.string.notif_actions_pref))?.run {
            summary = getString(Theming.getNotificationActionTitle(mRovePreferences.notificationActions.first))
            onPreferenceClickListener = this@PreferencesFragment
        }

        findPreference<Preference>(getString(R.string.filter_pref))?.run {
            RovePreferences.getPrefsInstance().filters?.let { ft ->
                summary = ft.size.toString()
                isEnabled = ft.isNotEmpty()
            }
        }

        findPreference<Preference>(getString(R.string.reset_sortings_pref))?.run {
            isEnabled = mRovePreferences.sortings != null && mRovePreferences.sortings?.isNotEmpty()!!
            onPreferenceClickListener = this@PreferencesFragment
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.accent_pref) -> RecyclerSheet.newInstance(RecyclerSheet.ACCENT_TYPE)
                .show(requireActivity().supportFragmentManager, RecyclerSheet.TAG_MODAL_RV)
            getString(R.string.active_tabs_pref) -> RecyclerSheet.newInstance(RecyclerSheet.TABS_TYPE)
                .show(requireActivity().supportFragmentManager, RecyclerSheet.TAG_MODAL_RV)
            getString(R.string.filter_pref) -> if (!mRovePreferences.filters.isNullOrEmpty()) {
                RecyclerSheet.newInstance(RecyclerSheet.FILTERS_TYPE)
                    .show(requireActivity().supportFragmentManager, RecyclerSheet.TAG_MODAL_RV)
            }
            getString(R.string.notif_actions_pref) -> RecyclerSheet.newInstance(RecyclerSheet.NOTIFICATION_ACTIONS_TYPE)
                .show(requireActivity().supportFragmentManager, RecyclerSheet.TAG_MODAL_RV)
            getString(R.string.reset_sortings_pref) -> Dialogs.showResetSortingsDialog(requireContext())
        }
        return false
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.precise_volume_pref) -> with(mMediaPlayerHolder) {
                setPreciseVolume(if (!mRovePreferences.isPreciseVolumeEnabled) {
                    mRovePreferences.latestVolume = currentVolumeInPercent
                    100
                } else {
                    mRovePreferences.latestVolume
                })
            }
            getString(R.string.playback_vel_pref) -> mMediaControlInterface.onPlaybackSpeedToggled()
            getString(R.string.theme_pref) -> mUIControlInterface.onAppearanceChanged(isThemeChanged = true)
            getString(R.string.theme_pref_black) -> mUIControlInterface.onAppearanceChanged(isThemeChanged = false)
            getString(R.string.eq_pref) -> if (mRovePreferences.isEqForced) {
                mMediaPlayerHolder.onBuiltInEqualizerEnabled()
            } else {
                mMediaPlayerHolder.releaseBuiltInEqualizer()
            }
            getString(R.string.focus_pref) -> with(mMediaPlayerHolder) {
                if (mRovePreferences.isFocusEnabled) {
                    tryToGetAudioFocus()
                    return
                }
                giveUpAudioFocus()
            }
            getString(R.string.covers_pref) -> mMediaControlInterface.onHandleCoverOptionsUpdate()
            getString(R.string.notif_actions_pref) ->
                findPreference<Preference>(getString(R.string.notif_actions_pref))?.summary =
                    getString(Theming.getNotificationActionTitle(mRovePreferences.notificationActions.first))
            getString(R.string.song_visual_pref) -> {
                mMediaPlayerHolder.updateMediaSessionMetaData()
                mMediaControlInterface.onUpdatePlayingAlbumSongs(null)
            }
            getString(R.string.rotation_pref) -> requireActivity().requestedOrientation = Theming.getOrientation()
            RovePreferences.PREFS_DETAILS_SORTING -> {
                updateResetSortingsOption()
                mMediaPlayerHolder.updateMediaSessionMetaData()
                mMediaControlInterface.onUpdatePlayingAlbumSongs(null)
            }
        }
    }

    fun enableEqualizerOption() {
        val eqEnabled = mRovePreferences.isEqForced
        findPreference<SwitchPreferenceCompat>(getString(R.string.eq_pref))?.run {
            isChecked = eqEnabled
            isEnabled = eqEnabled
            summary = if (!eqEnabled) {
                getString(R.string.error_builtin_eq)
            } else {
                getString(R.string.eq_pref_sum)
            }
        }
    }

    fun updateResetSortingsOption() {
        findPreference<Preference>(getString(R.string.reset_sortings_pref))?.run {
            isEnabled = mRovePreferences.sortings != null && mRovePreferences.sortings?.isNotEmpty()!!
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PreferencesFragment.
         */
        @JvmStatic
        fun newInstance() = PreferencesFragment()
    }
}
