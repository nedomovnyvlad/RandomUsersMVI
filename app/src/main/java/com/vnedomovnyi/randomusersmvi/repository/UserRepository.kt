package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.entity.User

interface UserRepository {
    fun getUsers(): List<User>
}