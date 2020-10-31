package com.thoughtworks.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        println(
            String.format(
                "Sending request %s on %s%n%s",
                request.url(),
                chain.connection(),
                request.headers()
            )
        )
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        println(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(),
                (t2 - t1) / 1e6,
                response.headers()
            )
        )
        // System.out.println(String.format("Response Data %s",response.body().string()));
        // System.out.println(String.format("Response Data %s",response.body().string()));
        return response
    }
}