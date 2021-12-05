package com.randomusersmvi.repository

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.vnedomovnyi.randomusersmvi.USER_COUNT
import com.vnedomovnyi.randomusersmvi.db.UserDao
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.repository.UserRepository
import com.vnedomovnyi.randomusersmvi.repository.UserRepositoryImpl
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException


class UserRepositoryTest {

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private lateinit var users: List<User>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        userRepository = UserRepositoryImpl(userService, userDao)

        users = listOf(
            User(1, "Name", "1", "")
        )
    }

    @Test
    fun loadUsers_loadsCacheOnlyIfCacheIsNotEmpty() {
        `when`(userDao.subscribe()).thenReturn(Observable.just(users))
        userRepository.loadUsers().test()
        verify(userService, never()).getUsers(USER_COUNT)
    }

    @Test
    fun loadUsers_returnCorrectValuesFromCacheIfCacheIsNotEmpty() {
        `when`(userDao.subscribe()).thenReturn(Observable.just(users))

        val testObserver = userRepository.loadUsers().test()

        testObserver.assertValueAt(0) { it.equals(users) }
        testObserver.assertValueCount(1)
    }

    @Test
    fun loadUsers_loadsFromNetworkIfCacheIsEmpty() {
        `when`(userDao.subscribe()).thenReturn(Observable.just(emptyList()))
        userRepository.loadUsers().test()
        verify(userService).getUsers(USER_COUNT)
    }

    @Test
    fun loadUsers_returnsNoErrorIfCacheIsNotEmptyAndNoConnection() {
        `when`(userDao.subscribe()).thenReturn(Observable.just(users))
        `when`(userService.getUsers(USER_COUNT)).thenReturn(Single.error(UnknownHostException()))
        val testObserver = userRepository.loadUsers().test()
        testObserver.assertNoErrors()
    }

    @Test
    fun loadUsers_returnsErrorIfCacheIsEmptyAndNoConnection() {
        `when`(userDao.subscribe()).thenReturn(Observable.just(emptyList()))
        `when`(userService.getUsers(USER_COUNT)).thenReturn(Single.error(UnknownHostException()))
        val testObserver = userRepository.loadUsers().test()
        testObserver.assertError(UnknownHostException::class.java)
    }

}