package com.vnedomovnyi.randomusersmvi.di

import android.content.Context
import androidx.room.Room
import com.vnedomovnyi.randomusersmvi.db.RandomUserDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideRandomUserDatabase(get()) }
    factory { get<RandomUserDatabase>().userDao() }
}

private fun provideRandomUserDatabase(context: Context) =
    Room.databaseBuilder(context, RandomUserDatabase::class.java, "random-user-database")
        .fallbackToDestructiveMigration()
        .build()