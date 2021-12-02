package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.USER_COUNT
import com.vnedomovnyi.randomusersmvi.db.UserDao
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import com.vnedomovnyi.randomusersmvi.retrofit.response.toUser

class UserRepositoryImpl(
    private val userService: UserService,
    private val userDao: UserDao,
) : UserRepository {

    override fun getUsers(): List<User> {
        val users = userDao.get()

        if (users.isNotEmpty()) {
            return users
        }

        val response = userService.getUsers(USER_COUNT).execute().body()
        val newUsers = response!!.users.map { it.toUser() }

        userDao.insert(newUsers)

        return newUsers
    }
}