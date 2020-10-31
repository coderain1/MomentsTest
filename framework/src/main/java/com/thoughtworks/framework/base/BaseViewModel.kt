package com.thoughtworks.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.framework.network.scheduler.IoToMain
import io.reactivex.Observable
import io.reactivex.Single

open class BaseViewModel : ViewModel() {
    val resStateLiveData = MutableLiveData<ResponseType>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun <T> Observable<T>.ioToMain(): Observable<T> {
        return this.compose(IoToMain<T>())
    }

    fun <T> Single<T>.ioToMain(): Single<T> {
        return this.compose(IoToMain<T>())
    }
}