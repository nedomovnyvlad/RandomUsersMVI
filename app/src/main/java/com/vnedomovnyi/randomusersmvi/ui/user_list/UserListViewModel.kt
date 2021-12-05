package com.vnedomovnyi.randomusersmvi.ui.user_list

import androidx.lifecycle.SavedStateHandle
import com.vnedomovnyi.randomusersmvi.ui.base.MviViewModel
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListActionProcessor
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult

class UserListViewModel(
    savedStateHandle: SavedStateHandle,
    actionProcessor: UserListActionProcessor
) :
    MviViewModel<UserListAction, UserListResult, UserListState>(
        savedStateHandle,
        actionProcessor,
        UserListState.default()
    ) {

    fun loadDataIfNeeded() {
        accept(UserListAction.LoadUsersAction)
    }

    fun deleteUser(userId: Int) {
        accept(UserListAction.DeleteUserAction(userId))
    }
}