package com.thoughtworks.momentstest.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.thoughtworks.momentstest.R
import com.thoughtworks.momentstest.model.bean.CommentBean
import com.thoughtworks.momentstest.toastDefault
import com.thoughtworks.momentstest.util.ClipBoardUtils


class CommentDialog(
        context: Context,
        private val commentItem: CommentBean?,
        private val position: Int) : Dialog(context, R.style.commentDialog), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_comment)
        initWindowParams()
        initView()
    }

    private fun initWindowParams() {
        val dialogWindow = window
        if (dialogWindow != null) {
            // 获取屏幕宽、高用
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            if (wm != null) {
                val display = wm.defaultDisplay
                val lp = dialogWindow.attributes
                // 宽度设置为屏幕的0.65
                lp.width = (display.width * 0.65).toInt()

                dialogWindow.setGravity(Gravity.CENTER)
                dialogWindow.attributes = lp
            }
        }
    }

    private fun initView() {
        val tvCopy = findViewById<TextView>(R.id.tvCopy)
        tvCopy.setOnClickListener(this)
        val tvDelete = findViewById<TextView>(R.id.tvDelete)
        tvDelete.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvCopy -> {
                dismiss()
                commentItem?.content?.let {
                    ClipBoardUtils.copy(context, it)
                }
            }
            R.id.tvDelete -> {
                dismiss()
                context.toastDefault("待实现")
            }
            else -> {
            }
        }
    }
}