package com.thoughtworks.momentstest.model.bean

import com.google.gson.annotations.SerializedName

class UserBean {
    /**
     * 轮廓图
     */
    @SerializedName("profile-image")
    val profileImage: String? = null

    /**
     * 头像
     */
    @SerializedName("avatar")
    val avatar: String? = null

    /**
     * 昵称
     */
    @SerializedName("nick")
    val nick: String? = null

    /**
     * 用户名
     */
    @SerializedName("username")
    val username: String? = null
}