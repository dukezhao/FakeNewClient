package com.example.z.fakenewclient.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:16:36
 * Mail:mrzhaoxiaolin@163.com
 */
open class BaseLazyFragment : Fragment() {
    protected var isVisiableToUser: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getContentId(), container, false)

        return view
    }

    open fun getContentId(): Int {
        return 0
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if(userVisibleHint)//visiable , then load page
        {
            isVisiableToUser=true
            onVisible()
        }else{
            isVisiableToUser=false
            onInvisiable()
        }
    }

    private fun onInvisiable() {


    }

    open fun onVisible() {
        lazyLoad()

    }

    open fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}