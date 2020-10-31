package com.thoughtworks.momentstest.view

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.thoughtworks.framework.base.BaseActivity
import com.thoughtworks.framework.base.ResponseType
import com.thoughtworks.framework.imageloader.ImageLoader
import com.thoughtworks.momentstest.R
import com.thoughtworks.momentstest.adapter.MomentAdapter
import com.thoughtworks.momentstest.dp2px
import com.thoughtworks.momentstest.model.bean.TweetBean
import com.thoughtworks.momentstest.model.bean.UserBean
import com.thoughtworks.momentstest.model.viewmodel.MomentsViewModel
import com.thoughtworks.momentstest.toastDefault
import kotlinx.android.synthetic.main.activity_moments.*
import qiu.niorgai.StatusBarCompat
import kotlin.math.abs

class MomentsActivity : BaseActivity<MomentsViewModel>(), LifecycleOwner {
    private val adapter: MomentAdapter by lazy {
        val instance = MomentAdapter(object : MomentAdapter.ActionCallback {
            /**
             * 点赞或取消点赞
             */
            override fun onLike(index: Int, tweet: TweetBean) {
                val self = viewModel.getSelfInfo()
                self?.let {
                    tweet.like(it)
                    adapter.notifyItemChanged(index)
                }
            }

            override fun doComment(index: Int, tweet: TweetBean) {
                toastDefault("暂未实现")
            }
        })
        instance.setEnableLoadMore(false)
        instance
    }

    override fun initIntentData() {}

    override fun onInitViewModel() {
        val momentsObserver = Observer<List<TweetBean>> {
            if (viewModel.pageNo < 2) {
                //刷新操作
                adapter.setNewData(it)
            } else {
                if (it != null) {
                    adapter.addData(it)
                }
            }
            refreshLayout.finishLoadMore()
            refreshLayout.finishRefresh()
        }
        val userProfile = Observer<UserBean> {
            tvName.text = it.nick ?: it.username
            ImageLoader.get().loadUrl(ivCover, it.profileImage ?: "")
            ImageLoader.get().loadRoundRectImage(ivAvatar, it.avatar ?: "", dp2px(12F).toInt())
        }
        viewModel.subscribe(this, momentsObserver, userProfile)

        viewModel.resStateLiveData.observe(this, Observer {
            if (it == ResponseType.TYPE_NO_MORE) {
                refreshLayout.finishRefresh(0, true, true)
            }
        })
    }

    override fun getLayoutResId() = R.layout.activity_moments

    override fun initViews() {
        StatusBarCompat.translucentStatusBar(this)
        refreshLayout.setOnRefreshListener {
            //下拉刷新
            viewModel.refresh()
        }
        refreshLayout.setOnLoadMoreListener {
            //上拉加载更多
            if (viewModel.noMoreData) {
                refreshLayout.finishLoadMore(0, true, true)
                return@setOnLoadMoreListener
            }
            viewModel.loadMomentsData(false)
        }
        collapseToolbar.setCollapsedTitleTextColor(Color.WHITE)
        collapseToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE))
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val color = Color.argb(200, 0, 0, 0)
            collapseToolbar.setCollapsedTitleTextColor(color)
            if (abs(verticalOffset) >= appBar.totalScrollRange) {
                //折叠状态
                collapseToolbar.title = "朋友圈"
                ivCover.visibility = View.GONE
                ivAvatar.visibility = View.GONE
            } else {
                //展开状态
                collapseToolbar.title = ""
                ivCover.visibility = View.VISIBLE
                ivAvatar.visibility = View.VISIBLE
            }
        })

        rvMoments.adapter = adapter
    }

    override fun loadData() {
        viewModel.loadUserProfile()
        viewModel.loadMomentsData(true)
    }
}