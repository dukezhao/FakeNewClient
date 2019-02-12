package com.example.modulaa.module.home

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.example.modulaa.Base.BaseAct
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import com.example.modulaa.R
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Author: Z.King.James
 * Declarations:main Activity
 * Created on: 2018/12/2:23:19
 * Mail:mrzhaoxiaolin@163.com
 */
class HomeAct : BaseAct(), HomeContract.IHomeView, OnBannerListener {

    lateinit var mHeadImg: ImageView


    lateinit var mToolbar: Toolbar
    lateinit var mAppbar: AppBarLayout
    lateinit var mTabLayout: DachshundTabLayout//导航切换tab
    lateinit var mViewPager: ViewPager
    lateinit var mNavView: NavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mBanner: Banner
    lateinit var mFab: FloatingActionButton


    //save the user press the system's back btn 's time
    private var mExitTime: Long = 0
    private val mHomePresenter: HomePresenter by lazy {

        HomePresenter(this)
    }

    override val contentViewlayoutId: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

        mHeadImg = main_head_img//imageview赋值 ?todo
        mToolbar = main_toolbar
        mAppbar = main_appbar
        mTabLayout = main_tab
        mViewPager = main_vp
        mNavView = nav_view
        mDrawerLayout = mainActivity
        mBanner = main_banner
        mFab = main_fab


        //对Android 4.4以上版本适配toolbar

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //set toolbar 高度为80 dp ,adapteration the system status bar
            var layParas=mToolbar.layoutParams
            layParas.height=ScreenUtil.dip2px(this,80f)
            mToolbar.layoutParams=layParas

        }

        //huawei bottom navigator adapteration
        if(AndroidWorkaround.checkDeviceHasNavigationBar(this))//AndroidWorkaround 是解决此问题的特定tools class
        {
            //AndroidWorkaround.assistActivity(findViewById(android.R.id.content))

            val assistActivity: Any = AndroidWorkaround.assistActivity(findViewById(android.R.id.content))

        }
        initDrawerLayout()

        mBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mBanner.setOnBannerListener(this)

    }

    private fun initDrawerLayout() {

        mNavView.inflateHeaderView(R.layout.layout_main_nav)
        val headerView=mNavView.getHeaderView(0)
    }


    override fun OnBannerClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBanner(imageUrls: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBannerFail(failMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}