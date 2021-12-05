package com.vnedomovnyi.randomusersmvi.network

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ChangeableBaseUrlInterceptor(
    private val changeableBaseUrl: ChangeableBaseUrl
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        changeableBaseUrl.url?.toHttpUrlOrNull()?.let { newUrl ->
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }

        return chain.proceed(request)
    }

}