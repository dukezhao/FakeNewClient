package com.example.z.fakenewclient.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:16:31
 * Mail:mrzhaoxiaolin@163.com
 */
class App:Application(){
    override fun onCreate() {
        super.onCreate()

        //arouter

        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}