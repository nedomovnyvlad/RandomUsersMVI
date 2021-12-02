package com.vnedomovnyi.randomusersmvi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vnedomovnyi.randomusersmvi.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun get(): List<User>

    @Insert
    fun insert(users: List<User>)
}