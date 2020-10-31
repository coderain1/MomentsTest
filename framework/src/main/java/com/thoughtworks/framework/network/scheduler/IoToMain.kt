package com.thoughtworks.framework.network.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IoToMain<T> : BaseScheduler<T>(Schedulers.io(),AndroidSchedulers.mainThread())