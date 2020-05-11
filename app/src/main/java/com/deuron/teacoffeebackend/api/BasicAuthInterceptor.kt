package com.deuron.teacoffeebackend.api

import java.io.IOException
import java.nio.charset.Charset
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(user: String, password: String) : Interceptor {
    private val credentials: String

    init {
        this.credentials = okhttp3.Credentials.basic(user, password, Charset.forName("UTF-8"))
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .header("Authorization", credentials)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
