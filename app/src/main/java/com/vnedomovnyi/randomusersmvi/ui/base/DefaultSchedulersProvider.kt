package com.vnedomovnyi.randomusersmvi.ui.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class DefaultSchedulersProvider private constructor() : SchedulersProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.io()

    override fun observationScheduler(): Scheduler = AndroidSchedulers.mainThread()

    companion object {
        val instance = DefaultSchedulersProvider()
    }
}