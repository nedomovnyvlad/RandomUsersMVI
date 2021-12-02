package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.entity.User

class UserRepositoryImpl : UserRepository {
    override fun getUsers(): List<User> {
        // TODO: Replace with a network request
        return listOf(
            User(
                "User",
                "1",
                "email",
                "city",
                "state",
                "https://lh3.googleusercontent.com/ykuq3KjWWVgwt9fV1zh1ZzAhXJF6pKV5tbUGH0BZIBBP5yIICcavfO-knvLifR1rv0uBiEnlngw=w640-h400-e365",
            ),
            User(
                "User",
                "2",
                "email",
                "city",
                "state",
                "https://lh3.googleusercontent.com/ykuq3KjWWVgwt9fV1zh1ZzAhXJF6pKV5tbUGH0BZIBBP5yIICcavfO-knvLifR1rv0uBiEnlngw=w640-h400-e365",
            ),
        )
    }
}