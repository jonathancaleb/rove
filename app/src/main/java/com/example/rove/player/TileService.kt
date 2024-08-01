package com.example.rove.player

import android.content.Intent
import android.app.PendingIntent
import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.example.rove.RoveConstants
import com.example.rove.ui.MainActivity

@RequiresApi(Build.VERSION_CODES.N)
class PlayerTileService : TileService() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(RoveConstants.LAUNCHED_BY_TILE, RoveConstants.LAUNCHED_BY_TILE)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        startActivityAndCollapse(pendingIntent)
    }
}
