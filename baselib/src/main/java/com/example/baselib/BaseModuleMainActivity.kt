package com.example.baselib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class BaseModuleMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_module_main)
    }
}
