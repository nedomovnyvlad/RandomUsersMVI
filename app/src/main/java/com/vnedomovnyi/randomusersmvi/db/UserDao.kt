package com.vnedomovnyi.randomusersmvi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vnedomovnyi.randomusersmvi.entity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun subscribe(): Observable<List<User>>

    @Insert
    fun insert(users: List<User>): Completable

    @Query("DELETE FROM users WHERE id = :id")
    fun delete(id: Int)
}