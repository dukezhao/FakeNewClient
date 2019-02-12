package com.example.z.fakenewclient.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:16:32
 * Mail:mrzhaoxiaolin@163.com
 */
abstract class BaseActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    open abstract fun layoutId():Int
}