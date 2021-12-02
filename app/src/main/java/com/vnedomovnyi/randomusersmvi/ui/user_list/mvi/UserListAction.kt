package com.vnedomovnyi.randomusersmvi.ui.user_list.mvi

import com.vnedomovnyi.randomusersmvi.ui.base.MviAction

sealed class UserListAction : MviAction {
    object LoadUsersAction : UserListAction()
}