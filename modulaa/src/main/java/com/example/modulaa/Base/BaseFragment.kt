package com.example.modulaa.Base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/2:21:22
 * Mail:mrzhaoxiaolin@163.com
 */
abstract class BaseFragment:Fragment(){
    private lateinit var unbinder:Unbinder
    /**
     * get layout Id
     * */

    protected abstract  val contentViewLayoutId:Int



    /**
     * UI init
     *
     * */

    protected abstract fun  init(view:View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return if(contentViewLayoutId!=0)
        {

            //todo
            inflater.inflate(contentViewLayoutId,container,false)
        }
        else
        {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unbinder=ButterKnife.bind(this,view)
        init(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}