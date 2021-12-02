package com.vnedomovnyi.randomusersmvi.ui.user_list.mvi

import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.ui.base.MviResult

sealed class UserListResult : MviResult {
    object InProgressResult : UserListResult()

    data class ErrorResult(val t: Throwable) : UserListResult()

    data class LoadUsersResult(val users: List<User>) : UserListResult()
}