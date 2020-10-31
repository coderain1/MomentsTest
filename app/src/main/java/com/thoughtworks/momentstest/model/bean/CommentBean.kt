package com.thoughtworks.momentstest.model.bean

import com.google.gson.annotations.SerializedName

class CommentBean {

    /**
     * 评论内容
     */
    @SerializedName("content")
    val content:String? = null

    /**
     * 评论人
     */
    @SerializedName("sender")
    val sender:UserBean? = null

}