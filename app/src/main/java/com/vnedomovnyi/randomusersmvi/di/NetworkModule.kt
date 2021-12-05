package com.vnedomovnyi.randomusersmvi.di

import com.google.gson.Gson
import com.vnedomovnyi.randomusersmvi.BASE_URL
import com.vnedomovnyi.randomusersmvi.retrofit.UserService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { Gson() }
    single { provideRetrofit(get(), get()) }
    single { provideUserService(get()) }
}

private fun provideOkHttpClient() =
    OkHttpClient.Builder()
        .build()

private fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()

private fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)