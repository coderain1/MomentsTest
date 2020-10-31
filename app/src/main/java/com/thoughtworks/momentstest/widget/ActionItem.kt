package com.thoughtworks.momentstest.widget

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


class ActionItem {
    /**
     * 定义图片对象
     */
    var drawable: Drawable? = null
    /**
     * 定义文本对象
     */
    var title: CharSequence

    constructor(drawable: Drawable, title: CharSequence) {
        this.drawable = drawable
        this.title = title
    }

    constructor(title: CharSequence) {
        this.drawable = null
        this.title = title
    }

    constructor(context: Context, titleId: Int, drawableId: Int) {
        this.title = context.resources.getText(titleId)
        this.drawable = ContextCompat.getDrawable(context, drawableId)
    }

    constructor(context: Context, title: CharSequence, drawableId: Int) {
        this.title = title
        this.drawable = ContextCompat.getDrawable(context, drawableId)
    }
}
