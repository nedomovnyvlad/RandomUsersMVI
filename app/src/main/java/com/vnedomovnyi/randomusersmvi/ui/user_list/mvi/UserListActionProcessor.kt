package com.vnedomovnyi.randomusersmvi.ui.user_list.mvi

import com.vnedomovnyi.randomusersmvi.ui.base.*
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListAction.LoadUsersAction
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListResult.*
import com.vnedomovnyi.randomusersmvi.use_case.GetUsers
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserListActionProcessor : MviActionsProcessor<UserListAction, UserListResult>(),
    KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val getUsers: GetUsers by inject()

    override fun getActionProcessors(shared: Observable<UserListAction>): List<Observable<UserListResult>> =
        listOf(
            shared.connect(loadUsersProcessor),
        )

    private val loadUsersProcessor = createActionProcessor<LoadUsersAction, UserListResult>(
        schedulersProvider,
        { InProgressResult },
        ::ErrorResult
    ) {
        onNextSafe(LoadUsersResult(getUsers()))
        onCompleteSafe()
    }
}