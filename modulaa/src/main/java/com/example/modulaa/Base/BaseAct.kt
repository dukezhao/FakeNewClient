package com.example.modulaa.Base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.view.MenuItem
import butterknife.ButterKnife
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.modulaa.R
import com.example.modulaa.StatusBarUtil
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/24 15:38
 * Mail:mrzhaoxiaolin@163.com
 */

@Route(path = "/modulaa/t2")
abstract class BaseAct : AppCompatActivity(), BGASwipeBackHelper.Delegate {

    var mCompositeSubscription: CompositeSubscription? = null //rx库
    private lateinit var mSwipeBackHelper: BGASwipeBackHelper

    /**
     * get layout Id
     * @return layoutId
     * */
    protected abstract val contentViewlayoutId: Int//资源文件id


    val compositeSubscription: CompositeSubscription?
        //声明变量，
        get() {//定义它的get 方法
            checkSubscription()//判空，确定下边返回非NPE
            return this.mCompositeSubscription
        }

    /**
     * prepare before UI initation
     *
     * */
    protected open fun beforeInit() {

    }

    /**
     * init layout and view controller
     * */

    abstract fun initView(savedInstanceState: Bundle?)

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    /**
     * check if is NPE
     * */
    private fun checkSubscription() {
        if (this.mCompositeSubscription == null)
            this.mCompositeSubscription = CompositeSubscription()
    }


    /**
     * 增加一个调度器 //todo 作用？
     * */

    fun addSubscription(s: Subscription) {
        checkSubscription()
        this.mCompositeSubscription!!.add(s)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        /*super.onCreate(savedInstanceState)
       */
        //todo
        //必须在application的oncreate里执行BGASwipeBackManager.getInstantce().init(this)来初始化滑动返回 ，
        //在  super.onCreate(savedInstanceState)调用该方法
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
        beforeInit()//未具体实现
        if (contentViewlayoutId != 0) {
            setContentView(contentViewlayoutId)//原：R.layout.activity_modula_a
            initView(savedInstanceState)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        //release CompositeSubscription
        if (this.mCompositeSubscription != null && !this.mCompositeSubscription!!.isUnsubscribed)//不为空且切未解绑
        {
            this.mCompositeSubscription!!.unsubscribe()
        }

    }

    /**
     * init the swipe & return back , before onCreate(savedInstanceState) to use it
     *
     * */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)


        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        mSwipeBackHelper.setSwipeBackEnable(true)
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)// 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)// 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)   // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setIsNeedShowShadow(true)  // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)  // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)// 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
    }

    /**
     * 是否支持滑动返回，这里在父类默认返回true ,来支持滑动返回，
     * 若某个界面不想支持滑动返回，则重写该方法返回false 即可。
     *
     * */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }


    /**
     * 正在滑动返回，
     *
     * @param slideOffset 从 0 到 1
     * */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {
    }

    /**
     * 没有达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     * */
    override fun onSwipeBackLayoutCancel() {
    }

    //BGA overload method
    /**
     * 滑动返回执行完，销毁当前act
     * */


    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }







    override fun onBackPressed() {
        //正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

    /**
     *todo 设置透明状态栏？
     * */
    fun setStatusBarTransparent() {

        StatusBarUtil.setTransparent(this)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */

    protected fun setStatusBarColor(@ColorInt color: Int) {
         setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
    }


    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */

    fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


