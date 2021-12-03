package com.vnedomovnyi.randomusersmvi

import android.app.Application
import com.vnedomovnyi.randomusersmvi.BuildConfig.DEBUG
import com.vnedomovnyi.randomusersmvi.di.*
import com.vnedomovnyi.randomusersmvi.ui.user_list.userListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
        private val MODULES = listOf(
            appModule,
            databaseModule,
            networkModule,
            repositoryModule,
            useCaseModule,
            userListModule,
        )
    }

}
