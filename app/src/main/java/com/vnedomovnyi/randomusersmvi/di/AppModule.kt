package com.vnedomovnyi.randomusersmvi.di

import com.vnedomovnyi.randomusersmvi.ui.base.DefaultSchedulersProvider
import com.vnedomovnyi.randomusersmvi.ui.base.SchedulersProvider
import org.koin.dsl.module

val appModule = module {
    single<SchedulersProvider> { DefaultSchedulersProvider.instance }
}