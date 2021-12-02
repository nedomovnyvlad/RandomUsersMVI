package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.USER_COUNT
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import com.vnedomovnyi.randomusersmvi.retrofit.response.toUser

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override fun getUsers(): List<User> {
        val response = userService.getUsers(USER_COUNT).execute().body()
        return response!!.users.map { it.toUser() }
    }
}