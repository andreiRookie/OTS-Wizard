package ru.otus.basicarchitecture.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val authRequest = currentRequest.newBuilder()
            .addHeader("Authorization", "Token $API_KEY")
            .build()

        return chain.proceed(authRequest)
    }

    companion object {
        private const val API_KEY = "c8b0fee524e14a3c0e9895947737e8200778aad8"
    }
}