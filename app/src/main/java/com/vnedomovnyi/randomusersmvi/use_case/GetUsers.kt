package com.vnedomovnyi.randomusersmvi.use_case

import com.vnedomovnyi.randomusersmvi.repository.UserRepository

class GetUsers(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUsers()
}