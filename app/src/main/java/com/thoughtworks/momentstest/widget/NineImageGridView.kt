package com.thoughtworks.momentstest.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.thoughtworks.framework.imageloader.ImageLoader
import com.thoughtworks.framework.imageloader.config.DiskCacheStrategy
import com.thoughtworks.framework.imageloader.config.ImageLoaderConfig
import com.thoughtworks.framework.imageloader.config.Priority
import com.thoughtworks.framework.imageloader.listener.ImageRequestListener
import com.thoughtworks.momentstest.R

class NineImageGridView : AbstractNineImageGridView {
    companion object {
        private const val MAX_W_H_RATIO = 3F
    }

    /**
     * 点击图片项Listener
     */
    var onItemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun displayOneImage(imageView: RatioImageView, url: String, parentWidth: Int): Boolean {
        ImageLoader.get().loadUrlWithListener(imageView, url, ImageLoaderConfig().apply {
            diskCacheStrategy = DiskCacheStrategy.ALL
            placeholderResId = R.mipmap.icon_placeholder
            errorResId = R.mipmap.icon_error
            fallbackResId = R.mipmap.icon_error
            priority = Priority.NORMAL
        }, object : ImageRequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable): Boolean {
                val w = resource.intrinsicWidth
                val h = resource.intrinsicHeight
                val newW: Int
                val newH: Int
                when {
                    h > w * MAX_W_H_RATIO -> {
                        //h:w = 5:3
                        newW = parentWidth / 2
                        newH = newW * 5 / 3
                    }
                    h < w -> {
                        //h:w = 2:3
                        newW = parentWidth * 2 / 3
                        newH = newW * 2 / 3
                    }
                    else -> {
                        //newH:h = newW :w
                        newW = parentWidth / 2
                        newH = h * newW / w
                    }
                }
                setOneImageLayoutParams(imageView, newW, newH)
                return false
            }

            override fun onLoadFailed(e: Throwable): Boolean {
                return false
            }
        })
        return false
    }

    override fun displayImage(imageView: RatioImageView, url: String) {
        ImageLoader.get().loadUrl(imageView, url)
    }

    override fun onClickImage(view: View, position: Int, url: String, imageUrls: List<String>) {
        onItemClickListener?.onItemClick(view, position)
    }

    /**
     * 点击图片项Listener
     */
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
