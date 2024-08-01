package com.example.rove.preferences

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rove.RoveConstants
import com.example.rove.RovePreferences
import com.example.rove.databinding.NotificationActionsItemBinding
import com.example.rove.models.NotificationAction
import com.example.rove.utils.Theming


class NotificationActionsAdapter: RecyclerView.Adapter<NotificationActionsAdapter.CheckableItemsHolder>() {

    var selectedActions = RovePreferences.getPrefsInstance().notificationActions

    private val mActions = listOf(
        NotificationAction(RoveConstants.REPEAT_ACTION, RoveConstants.CLOSE_ACTION), // default
        NotificationAction(RoveConstants.REWIND_ACTION, RoveConstants.FAST_FORWARD_ACTION),
        NotificationAction(RoveConstants.FAVORITE_ACTION, RoveConstants.CLOSE_ACTION),
        NotificationAction(RoveConstants.FAVORITE_POSITION_ACTION, RoveConstants.CLOSE_ACTION)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckableItemsHolder {
        val binding = NotificationActionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckableItemsHolder(binding)
    }

    override fun getItemCount() = mActions.size

    override fun onBindViewHolder(holder: CheckableItemsHolder, position: Int) {
        holder.bindItems()
    }

    inner class CheckableItemsHolder(private val binding: NotificationActionsItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItems() {

            with(binding) {

                val context = binding.root.context

                notifAction0.setImageResource(
                    Theming.getNotificationActionIcon(mActions[absoluteAdapterPosition].first, isNotification = false)
                )
                notifAction1.setImageResource(
                    Theming.getNotificationActionIcon(mActions[absoluteAdapterPosition].second, isNotification = false)
                )
                radio.isChecked = selectedActions == mActions[absoluteAdapterPosition]

                root.contentDescription = context.getString(Theming.getNotificationActionTitle(mActions[absoluteAdapterPosition].first))

                root.setOnClickListener {
                    notifyItemChanged(mActions.indexOf(selectedActions))
                    selectedActions = mActions[absoluteAdapterPosition]
                    notifyItemChanged(absoluteAdapterPosition)
                    RovePreferences.getPrefsInstance().notificationActions = selectedActions
                }

                root.setOnLongClickListener {
                    Toast.makeText(
                        context,
                        Theming.getNotificationActionTitle(mActions[absoluteAdapterPosition].first),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnLongClickListener true
                }
            }
        }
    }
}
