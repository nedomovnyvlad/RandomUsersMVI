package com.randomusersmvi.ui.user_list

import androidx.lifecycle.SavedStateHandle
import com.randomusersmvi.ui.base.ImmediateSchedulerProvider
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.ui.user_list.UserListState
import com.vnedomovnyi.randomusersmvi.ui.user_list.UserListViewModel
import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListActionProcessor
import com.vnedomovnyi.randomusersmvi.use_case.DeleteUser
import com.vnedomovnyi.randomusersmvi.use_case.LoadUsers
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserListViewModelTest {

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var loadUsers: LoadUsers

    @Mock
    private lateinit var deleteUser: DeleteUser

    private lateinit var userListViewModel: UserListViewModel

    private lateinit var users: List<User>

    @Before
    fun setupTasksViewModel() {
        MockitoAnnotations.openMocks(this)

        userListViewModel = UserListViewModel(
            savedStateHandle,
            UserListActionProcessor(
                ImmediateSchedulerProvider(),
                loadUsers,
                deleteUser
            )
        )

        users = listOf(
            User(1, "Name", "1", "")
        )
    }

    @Test
    fun loadAllUsersOnStart() {
        `when`(loadUsers.invoke()).thenReturn(Observable.just(users))

        val testObserver = createTestObserver()

        userListViewModel.loadDataIfNeeded()

        testObserver.assertValueAt(1, UserListState::isLoading)
        testObserver.assertValueAt(2) { !it.isLoading }
        testObserver.assertValueAt(2) { it.users == users }
    }

    @Test
    fun errorWhileLoadingUsers_showsError() {
        `when`(loadUsers.invoke()).thenReturn(Observable.error(Exception()))

        val testObserver = createTestObserver()

        userListViewModel.loadDataIfNeeded()

        testObserver.assertValueAt(2) { it.error != null }
    }

    @Test
    fun deleteUserSuccessfulAttempt_showsMessage() {
        `when`(loadUsers.invoke()).thenReturn(Observable.just(users))

        val testObserver = createTestObserver()

        userListViewModel.deleteUser(1)

        testObserver.assertValueAt(1) { it.deletedUserEvent != null }
    }

    private fun createTestObserver() = userListViewModel.viewStatesObservable.test()

}