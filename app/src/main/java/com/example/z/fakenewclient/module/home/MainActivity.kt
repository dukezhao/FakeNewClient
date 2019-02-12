package com.example.z.fakenewclient.module.home

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.z.fakenewclient.R
import com.example.z.fakenewclient.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/11:14:13
 * Mail:mrzhaoxiaolin@163.com
 */
class MainActivity : BaseActivity(), MainActivityView {

    var fragments: List<Fragment>? = null
    var adapter: HomeVpAdapter? = null
    var presenter: MainActPresenter? = null

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    //import fragments
    override fun onFragmentFetched(fragmentList: List<Fragment>) {
        fragments = fragmentList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDatas()
        initView()
    }

    // //init the viewpager ：adapter，offscreenpageLimit, settablayout ,
    private fun initView() {
        adapter = HomeVpAdapter(supportFragmentManager, fragments!!)
        vp.adapter = adapter
        vp.offscreenPageLimit = 5
        tablayout.setupWithViewPager(vp)


        //模块内跳转
        tv_title.setOnClickListener { ARouter.getInstance().build("/test/t1").navigation() }

        //模块间跳转
        tv_title2.setOnClickListener{ ARouter.getInstance().build("/modulaa/t2").navigation()}
    }

    private fun initDatas() {
        presenter = MainActPresenter(this)
        fragments=ArrayList<Fragment>()
        presenter?.fetchFragments(7)//why 7?todo above is 5

    }
}