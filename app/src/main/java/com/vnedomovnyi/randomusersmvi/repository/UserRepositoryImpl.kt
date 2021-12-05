package com.vnedomovnyi.randomusersmvi.repository

import com.vnedomovnyi.randomusersmvi.USER_COUNT
import com.vnedomovnyi.randomusersmvi.db.UserDao
import com.vnedomovnyi.randomusersmvi.entity.User
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import com.vnedomovnyi.randomusersmvi.retrofit.response.toUser
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class UserRepositoryImpl(
    private val userService: UserService,
    private val userDao: UserDao,
) : UserRepository {

    override fun loadUsers(): Observable<List<User>> {
        return loadUsersFromCache()
            .filter { list -> list.isNotEmpty() }
            .switchIfEmpty(loadUsersFromNetwork())
            .toObservable()
            .mergeWith(userDao.subscribe().skip(1))
    }

    private fun loadUsersFromNetwork(): Single<List<User>> {
        return Single.fromCallable { userService.getUsers(USER_COUNT).execute() }
            .map { it.body() }
            .map { it!!.users.map { apiUser -> apiUser.toUser() } }
            .flatMapCompletable { userDao.insert(it) }
            .andThen(loadUsersFromCache())
    }

    private fun loadUsersFromCache() = userDao.subscribe().firstOrError()

    override fun deleteUser(userId: Int) {
        userDao.delete(userId)
    }
}