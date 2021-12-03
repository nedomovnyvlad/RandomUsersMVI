package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.entity.User
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun loadUsers(): Observable<List<User>>
    fun deleteUser(userId: Int)
}