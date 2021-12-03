package com.vnedomovnyi.randomusersmvi.ui.user_list.mvi

import com.vnedomovnyi.randomusersmvi.ui.base.*
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction.DeleteUserAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction.LoadUsersAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult.*
import com.vnedomovnyi.randomusersmvi.use_case.DeleteUser
import com.vnedomovnyi.randomusersmvi.use_case.LoadUsers
import io.reactivex.rxjava3.core.Observable

class UserListActionProcessor(
    private val schedulersProvider: SchedulersProvider,
    private val loadUsers: LoadUsers,
    deleteUser: DeleteUser,
) : MviActionsProcessor<UserListAction, UserListResult>() {

    override fun getActionProcessors(shared: Observable<UserListAction>): List<Observable<UserListResult>> =
        listOf(
            shared.connect(loadUsersProcessor),
            shared.connect(deleteUserProcessor),
        )

    private val loadUsersProcessor =
        createObservableActionProcessor<LoadUsersAction, UserListResult>(
            schedulersProvider,
            { InProgressResult },
            ::ErrorResult,
            loadUsers().map { users -> LoadUsersResult(users) }
        )

    private val deleteUserProcessor =
        createUnitActionProcessor<DeleteUserAction, UserListResult>(
            schedulersProvider = schedulersProvider,
            onErrorResult = ::ErrorResult
        ) { action ->
            deleteUser.invoke(action.userId)
            onCompleteSafe()
        }
}