package com.vnedomovnyi.randomusersmvi

import android.app.Application
import com.vnedomovnyi.randomusersmvi.BuildConfig.DEBUG
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

class RandomUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@RandomUserApplication)
            modules(MODULES)
        }
    }

    companion object {
        private val MODULES = listOf<Module>()
    }

}
