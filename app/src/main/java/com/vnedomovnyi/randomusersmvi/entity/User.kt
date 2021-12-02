package com.vnedomovnyi.randomusersmvi.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vnedomovnyi.randomusersmvi.ROOM_UNSET_VALUE
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = ROOM_UNSET_VALUE,
    val firstName: String,
    val lastName: String,
    val email: String,
    val city: String,
    val state: String,
    val photoUrl: String
) : Parcelable