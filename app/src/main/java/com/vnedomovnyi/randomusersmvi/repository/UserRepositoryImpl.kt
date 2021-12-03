package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.USER_COUNT
import com.vnedomovnyi.randomusersmvi.db.UserDao
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import com.vnedomovnyi.randomusersmvi.retrofit.response.toUser
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl(
    private val userService: UserService,
    private val userDao: UserDao,
) : UserRepository {

    override fun loadUsers(): Observable<List<User>> {
        return userDao.subscribe().firstOrError()
            .filter { list -> list.isNotEmpty() }
            .switchIfEmpty(loadUsersFromNetwork())
            .toObservable()
            .mergeWith(userDao.subscribe())
    }

    private fun loadUsersFromNetwork(): Maybe<List<User>> {
        return Single.fromCallable { userService.getUsers(USER_COUNT).execute() }
            .map { it.body() }
            .map { it!!.users.map { apiUser -> apiUser.toUser() } }
            .flatMapCompletable { userDao.insert(it) }
            .toMaybe()
    }
}