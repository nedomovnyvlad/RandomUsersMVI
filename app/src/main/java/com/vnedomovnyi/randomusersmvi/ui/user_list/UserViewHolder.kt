package com.vnedomovnyi.randomusersmvi.ui.user_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vnedomovnyi.randomusersmvi.R
import com.vnedomovnyi.randomusersmvi.databinding.ItemUserBinding
import com.vnedomovnyi.randomusersmvi.entity.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemUserBinding.bind(itemView)

    fun bind(user: User) {
        val name = "${user.firstName} ${user.lastName}"
        binding.name.text = name

        Glide.with(itemView)
            .load(user.photoUrl)
            .circleCrop()
            .into(binding.photo)
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return UserViewHolder(itemView)
        }
    }
}