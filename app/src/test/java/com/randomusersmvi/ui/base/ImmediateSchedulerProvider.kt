package com.randomusersmvi.ui.base

import com.vnedomovnyi.randomusersmvi.ui.base.SchedulersProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class ImmediateSchedulerProvider : SchedulersProvider {

    override fun subscriptionScheduler(): Scheduler = Schedulers.trampoline()

    override fun observationScheduler(): Scheduler = Schedulers.trampoline()
}