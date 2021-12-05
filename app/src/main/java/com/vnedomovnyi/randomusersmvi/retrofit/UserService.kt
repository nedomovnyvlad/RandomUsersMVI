package com.vnedomovnyi.randomusersmvi.retrofit

import com.vnedomovnyi.randomusersmvi.retrofit.response.GetUsersResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("api?nat=US")
    fun getUsers(@Query("results") count: Int): Single<GetUsersResponse>

}