package com.vnedomovnyi.randomusersmvi.ui.user_list.mvi

import com.vnedomovnyi.randomusersmvi.ui.base.MviActionsProcessor
import com.vnedomovnyi.randomusersmvi.ui.base.SchedulersProvider
import com.vnedomovnyi.randomusersmvi.ui.base.createObservableActionProcessor
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction.LoadUsersAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult.*
import com.vnedomovnyi.randomusersmvi.use_case.LoadUsers
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserListActionProcessor : MviActionsProcessor<UserListAction, UserListResult>(),
    KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val loadUsers: LoadUsers by inject()

    override fun getActionProcessors(shared: Observable<UserListAction>): List<Observable<UserListResult>> =
        listOf(
            shared.connect(loadUsersProcessor),
        )

    private val loadUsersProcessor =
        createObservableActionProcessor<LoadUsersAction, UserListResult>(
            schedulersProvider,
            { InProgressResult },
            ::ErrorResult,
            loadUsers().map { users -> LoadUsersResult(users) }
        )
}