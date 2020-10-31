package com.thoughtworks.momentstest.model.bean

import com.google.gson.annotations.SerializedName


class TweetBean {

    @SerializedName("content")
    val content: String? = null

    @SerializedName("images")
    val images: List<ImageBean>? = null

    @SerializedName("sender")
    val sender: UserBean? = null

    @SerializedName("comments")
    val comments: List<CommentBean>? = null

    /**
     * 点赞的人
     */
    private val likes: MutableSet<UserBean> by lazy {
        val user = mutableSetOf<UserBean>()
        user
    }

    /**
     * 过滤数据，判断是否为有效数据
     * @return return true when data is correct otherwise false
     */
    fun isCorrect(): Boolean {
        return sender != null || images != null || comments != null || content != null
    }

    /**
     * 获取点选赞列表
     */
    fun getLikeUsers() = likes.toList()

    /**
     * 某人点赞/取消点赞
     * @return return true if someone like otherwise false
     */
    fun like(user: UserBean): Boolean {
        if (likes.contains(user)) {
            likes.remove(user)
            return false
        }
        return likes.add(user)
    }



}