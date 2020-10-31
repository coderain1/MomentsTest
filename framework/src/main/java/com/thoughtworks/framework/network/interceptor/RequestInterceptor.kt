package com.thoughtworks.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorised =
            request.newBuilder() //.header("ut", DataHelper.getStringSF(App.getContext(), SpConsts.USER_TOKEN))
                .build()
        return chain.proceed(authorised)
    }
}