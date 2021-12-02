package com.vnedomovnyi.randomusersmvi.di

import com.vnedomovnyi.randomusersmvi.repository.UserRepository
import com.vnedomovnyi.randomusersmvi.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserRepository> { UserRepositoryImpl() }
}