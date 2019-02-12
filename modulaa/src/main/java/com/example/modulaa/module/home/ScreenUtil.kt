package com.example.modulaa.module.home

import android.content.Context

object ScreenUtil {





    fun dip2px(context: Context, dipValue: Float):Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue*scale+0.5f).toInt()
    }

}
