package com.vnedomovnyi.randomusersmvi.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vnedomovnyi.randomusersmvi.entity.User


@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class RandomUserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}