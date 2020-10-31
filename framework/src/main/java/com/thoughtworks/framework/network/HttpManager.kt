package com.thoughtworks.framework.network

import android.content.Context
import com.thoughtworks.framework.network.interceptor.LogInterceptor
import com.thoughtworks.framework.network.interceptor.RequestInterceptor
import com.thoughtworks.framework.network.scheduler.IoToMain
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpManager {

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: HttpManager? = null

        fun getInstance(): HttpManager {
            return instance ?: synchronized(this) {
                instance ?: HttpManager().also { instance = it }
            }
        }
    }

    private val DEFAULT_TIMEOUT = 20
    private var retrofit: Retrofit? = null

    init{
        // okhttp
        val client = OkHttpClient.Builder()
        client.connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.addInterceptor(LogInterceptor())
        client.addInterceptor(RequestInterceptor())
        // retrofit
        retrofit = Retrofit.Builder()
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiConst.BASE_URL)
            .build()
    }
    fun <T> createService(service: Class<T>?): T {
        return retrofit!!.create(service)
    }
//    fun <T> applySchedulers(single: Single<T>): Single<T> {
//       return single.compose(IoToMain<T>())
//    }

    fun <T> Observable<T>.ioToMain(): Observable<T> {
        return this.compose(IoToMain<T>())
    }

}