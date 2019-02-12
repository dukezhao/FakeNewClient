package com.example.modulaa.wdiget.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/7:16:30
 * Mail:mrzhaoxiaolin@163.com
 */
class ScaleUpShowBehavior(context: Context,attributes: AttributeSet):FloatingActionButton.Behavior(){
    private var isAnimatingOut=false
private  val  viewPropertyChangeListener:ViewPropertyAnimatorListener=object :ViewPropertyAnimatorListener{
    override fun onAnimationEnd(view: View) {

        isAnimatingOut=false
        view.visibility=View.GONE
    }

    override fun onAnimationCancel(view: View) {
        isAnimatingOut=false
    }

    override fun onAnimationStart(p0: View?) {
        isAnimatingOut=true    }

}

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                     directTargetChild: View, target: View, axes: Int, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_HORIZONTAL
        //判断是否在水平主轴上滑动
        }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target:
    View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    if((dyConsumed>0))

    }
}