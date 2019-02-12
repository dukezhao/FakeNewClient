package com.example.modulaa.module.home

import android.app.assist.AssistContent
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

/**
 * Author: Z.King.James
 * Declarations:特定解决华为底部导航条适配工具类
 * Created on: 2017/12/18
 * Mail:mrzhaoxiaolin@163.com
 */


class AndroidWorkaround
private constructor(private val mChildOfContent: View) {
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: ViewGroup.LayoutParams

    init {
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {

            frameLayoutParams.height = usableHeightNow
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }

    }

    fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

    //伴生对象

    companion object {

        //todo

        fun assistActivity(content: View) {
            AndroidWorkaround(content)
        }

        PermissionFilter

    }
}
