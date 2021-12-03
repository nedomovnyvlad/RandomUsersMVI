package com.vnedomovnyi.randomusersmvi.di

import com.vnedomovnyi.randomusersmvi.use_case.LoadUsers
import org.koin.dsl.module

val useCaseModule = module {
    factory { LoadUsers(get()) }
}