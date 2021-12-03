package com.vnedomovnyi.randomusersmvi.use_case

import com.vnedomovnyi.randomusersmvi.repository.UserRepository

class LoadUsers(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.loadUsers()
}