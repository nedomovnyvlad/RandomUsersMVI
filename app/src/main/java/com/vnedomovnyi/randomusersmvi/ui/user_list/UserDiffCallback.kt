package com.vnedomovnyi.randomusersmvi.ui.user_list

import androidx.recyclerview.widget.DiffUtil
import com.vnedomovnyi.randomusersmvi.entity.User

object UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}