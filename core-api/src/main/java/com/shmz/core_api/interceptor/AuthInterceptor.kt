package com.shmz.core_api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("accept", "application/xml")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}