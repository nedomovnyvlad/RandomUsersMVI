package com.vnedomovnyi.randomusersmvi.ui.user_list

import com.vnedomovnyi.randomusersmvi.ui.user_list.mvi.UserListActionProcessor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userListModule = module {
    factory { UserListActionProcessor(get(), get(), get()) }
    viewModel { UserListViewModel(get(), get()) }
}