package com.example.modulaa.Base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import butterknife.ButterKnife
import com.example.modulaa.R

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/2:21:13
 * Mail:mrzhaoxiaolin@163.com
 */
abstract class BaseDialog
@JvmOverloads constructor(private  val mContext:Context,
                          layoutId:Int,styleId:Int= R.style.MyDialog):Dialog(mContext,styleId){
    init {
        val inflater=mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=inflater.inflate(layoutId,null)
        this.setContentView(view)
        ButterKnife.bind(this)
    }
}