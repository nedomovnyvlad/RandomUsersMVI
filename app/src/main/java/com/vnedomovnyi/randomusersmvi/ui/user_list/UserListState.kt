package com.vnedomovnyi.randomusersmvi.ui.user_list

import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.ui.base.MviViewState
import com.vnedomovnyi.randomusersmvi.ui.base.ViewStateEmptyEvent
import com.vnedomovnyi.randomusersmvi.ui.base.ViewStateErrorEvent
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserListState(
    val isLoading: Boolean,
    val users: List<User>?,
    val error: ViewStateErrorEvent?,
    val deletedUserEvent: ViewStateEmptyEvent?,
) : MviViewState<UserListResult> {

    companion object {
        fun default() = UserListState(false, null, null, null)
    }

    override fun reduce(result: UserListResult): MviViewState<UserListResult> {
        return when (result) {
            is InProgressResult -> result.reduce()
            is ErrorResult -> result.reduce()
            is LoadUsersResult -> result.reduce()
            is DeleteUserResult -> result.reduce()
        }
    }

    @Suppress("unused")
    private fun InProgressResult.reduce() = this@UserListState.copy(
        isLoading = true
    )

    private fun ErrorResult.reduce() = this@UserListState.copy(
        isLoading = false,
        error = ViewStateErrorEvent(t)
    )

    private fun LoadUsersResult.reduce() = this@UserListState.copy(
        isLoading = false,
        users = users
    )

    @Suppress("unused")
    private fun DeleteUserResult.reduce() = this@UserListState.copy(
        deletedUserEvent = ViewStateEmptyEvent()
    )

    override fun isSavable() = !isLoading
}