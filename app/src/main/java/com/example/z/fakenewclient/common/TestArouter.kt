package com.example.z.fakenewclient.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.z.fakenewclient.R

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:17:58
 * Mail:mrzhaoxiaolin@163.com
 */
@Route(path="/test/t1")
class TestArouter:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
    }
}