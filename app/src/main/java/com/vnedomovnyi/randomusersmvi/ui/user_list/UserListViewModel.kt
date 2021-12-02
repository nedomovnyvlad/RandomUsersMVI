package com.vnedomovnyi.randomusersmvi.ui.user_list

import androidx.lifecycle.SavedStateHandle
import com.vnedomovnyi.randomusersmvi.ui.base.MviViewModel
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListActionProcessor
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult

class UserListViewModel(savedStateHandle: SavedStateHandle) :
    MviViewModel<UserListAction, UserListResult, UserListState>(
        savedStateHandle,
        UserListActionProcessor(),
        UserListState.default()
    ) {

    fun loadDataIfNeeded() {
        if (viewState.users == null) {
            accept(UserListAction.LoadUsersAction)
        }
    }
}