package com.vnedomovnyi.randomusersmvi.ui.base

import io.reactivex.rxjava3.core.Scheduler

interface SchedulersProvider {

    fun subscriptionScheduler(): Scheduler

    fun observationScheduler(): Scheduler
}