package com.vnedomovnyi.randomusersmvi.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val city: String,
    val state: String,
    val photoUrl: String
) : Parcelable