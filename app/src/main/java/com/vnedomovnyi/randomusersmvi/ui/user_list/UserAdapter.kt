package com.vnedomovnyi.randomusersmvi.ui.user_list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vnedomovnyi.randomusersmvi.entity.User

class UserAdapter : ListAdapter<User, UserViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position))
}