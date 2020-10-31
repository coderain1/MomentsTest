package com.thoughtworks.momentstest.model.service

import com.thoughtworks.momentstest.model.bean.TweetBean
import com.thoughtworks.momentstest.model.bean.UserBean
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MomentService {

    @GET("user/{user_name}")
    fun getUserInfo(@Path("user_name") userName: String = "jsmith"): Single<UserBean>

    @GET("user/{user_name}/tweets")
    fun getTweets(@Path("user_name") userName: String = "jsmith"): Single<List<TweetBean>>
}