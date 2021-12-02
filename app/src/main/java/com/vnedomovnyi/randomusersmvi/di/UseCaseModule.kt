package com.vnedomovnyi.randomusersmvi.di

import com.vnedomovnyi.randomusersmvi.use_case.GetUsers
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUsers(get()) }
}