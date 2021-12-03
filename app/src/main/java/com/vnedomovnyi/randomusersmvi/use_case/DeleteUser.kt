package com.vnedomovnyi.randomusersmvi.use_case

import com.vnedomovnyi.randomusersmvi.repository.UserRepository

class DeleteUser(private val userRepository: UserRepository) {
    operator fun invoke(userId: Int) = userRepository.deleteUser(userId)
}