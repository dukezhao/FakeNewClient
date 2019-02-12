package com.example.modulaa

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

import com.example.modulaa.R.*

import java.io.Serializable

/**
 * Author: Z.King.James
 * Declarations: StatusBar 's utilty tools
 * Created on: 2018/10/24 15:38
 * Mail:mrzhaoxiaolin@163.com
 */

object StatusBarUtil {
  //  const val DEFAULT_STATUS_BAR_ALPHA = 112
/*    private const val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view
    private const val FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view*/
 //   private const val TAG_KEY_HAVE_SET_OFFSET = -123


    const val DEFAULT_STATUS_BAR_ALPHA = 112
    @JvmField val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view//像源码上边一样会报错《const val initializer should be a constant value》，
    //加上@JvmField后，ok,参考https://droidyue.com/blog/2017/11/05/dive-into-kotlin-constants/
    @JvmField val FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view
    private const val TAG_KEY_HAVE_SET_OFFSET = -123


    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color 状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */

    @JvmOverloads
    fun setColor(
            activity: Activity, @ColorInt color: Int,
            statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA
    ) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)//给系统状态栏着色第一步准备
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView =
                    activity.window.decorView as ViewGroup//as is not safety convert 不安全的。 Kotlin 中的不安全转换由中缀操作符 as
            //val x: String? = y as? String
            /*请注意，尽管事实上 as? 的右边是一个非空类型的 String，但是其转换的结果是可空*/
            val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            } else {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha))
            }
            setRootView(activity)

        }
    }

    /**
     *
     * 为滑动返回界面设置状态栏颜色
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     **/
    @JvmOverloads
    fun setColorForSwipeBack(activity: Activity, @ColorInt color: Int, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //todo
            val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
            val rootView = contentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(activity)
            if (rootView != null && rootView is CoordinatorLayout) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    rootView.fitsSystemWindows = true
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                    val isNeedRequestLayout = contentView.paddingTop < statusBarHeight
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0)
                        rootView.post { rootView.requestLayout() }
                    }
                } else {
                    rootView.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0)
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))


            }
            setTransparentForWindow(activity)

        }
    }

    /**
     * set transparent for window
     * */
    private fun setTransparentForWindow(activity: Activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = Color.TRANSPARENT
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                    .FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * set statusbar with pure color , dont setup the half transparent effort
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * */

    fun setClorNoTranslucent(activity: Activity, @ColorInt color: Int) {
        setColor(activity, color, 0)

    }


    /**
     * set statusbar with color (5.0 under will has not transparent effort ,so do not recomend this )
     * **/

    @Deprecated("")
    fun setColorDiff(activity: Activity, @ColorInt color: Int) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        // 移除半透明矩形,以免叠加
        val fakeStatusBarView = contentView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentView.addView(createStatusBarView(activity, color))
        }
        setRootView(activity)
    }


    /**
     * 使得状态栏半透明 make sysstatusbar half transLucent
     * */
    fun setTransLucent(activity: Activity, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

            return
        }
        setTransLucent(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */

    fun setTranslucentForCoordinatorLayout(activity: Activity, statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        addTranslucentView(activity, statusBarAlpha)
    }


    /**
     * 添加半透明矩形条 add half-translucent retangle view
     *
     * */
    private fun addTranslucentView(activity: Activity, statusBarAlpha: Int) {
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val fakeTranslucentView = contentView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.visibility == View.GONE) {
                fakeTranslucentView.visibility = View.VISIBLE
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))

        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
        }
    }

    /**
     * create half-translucent retangle view
     *
     * @param alpha 透明值
     * @return 半透明 ICategoryView
     *
     * */
    private fun createTranslucentStatusBarView(activity: Activity, alpha: Int): View {
        //draw a retangle with same height of the sys statusbar

        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        statusBarView.id + FAKE_TRANSLUCENT_VIEW_ID
        return statusBarView

    }


    /**
     * 计算状态栏颜色
     * cal status  color
     * @param color color value
     * @param alpha alpha value
     * @return  the final color of statusBar
     **/
    //@ColorInt注解，表示你期望的是一个代表颜色的整数值：
    private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }

        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff //shr(bits) => 有符号向左移 (类似Java的>>)
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue


    }

    /**
     * create a same size & shape half-Transparent rectangle
     **/
    private fun createStatusBarView(
            activity: Activity, @ColorInt color: Int,
            alpha: Int = 0
    ): View {

        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)

        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity)
        )//获取宽高参数
        statusBarView.layoutParams = params

        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        statusBarView.id = FAKE_TRANSLUCENT_VIEW_ID
        return statusBarView


    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @ return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {

        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }


    /**
     * 设置根布局参数,遍历根布局中子view,如果是viewgroup则，设置满足系统屏幕大小和clipToPadding=true
     */
    private fun setRootView(activity: Activity) {
        val parent = activity.findViewById<View>(android.R.id.content) as ViewGroup
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView=parent.getChildAt(i)
            if(childView is ViewGroup)
            {
                childView.fitsSystemWindows=true
                childView.clipToPadding=true
            }
            i++
        }
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {

            return
        }
        transparentStatusBar(activity)
        setRootView(activity)

    }

    /**
     * 使状态栏透明(5.0以上半透明效果,不建议使用)
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    @Deprecated("")
    fun setTranslucentDiff(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }
    }

    /**
     * 为Drawerlayout 布局设置状态栏颜色，纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     * */

    fun setCorlorNoTranslucentForDrawerlayout(activity: Activity, drawerlayout: DrawerLayout, @ColorInt color: Int) {
        setColorForDrawerLayout(activity, drawerlayout, color, 0)
    }

    /**
     * 布局设置状态栏变色
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     * */
    @JvmOverloads
    private fun setColorForDrawerLayout(activity: Activity, drawerlayout: DrawerLayout, @ColorInt color: Int, statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)// for sdk < 19 status
        {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)// for sdk >=21  status
        {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT

        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        //create a statusbar size 's retangle
        //add statusbar to layout
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        val contentLayout = drawerlayout.getChildAt(0) as ViewGroup
        val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentLayout.addView(createStatusBarView(activity, color), 0)//对根布局加入statusBar
        }
        //when content view is not LinearLayout , set padding top

        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.paddingLeft, getStatusBarHeight(activity) + contentLayout.paddingTop, contentLayout.paddingRight, contentLayout.paddingBottom)
        }
        //设置属性
        setDrawerLayoutProperty(drawerlayout, contentLayout)
        addTranslucentView(activity, statusBarAlpha)
    }


    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */

    private fun setDrawerLayoutProperty(drawerlayout: DrawerLayout, drawerLayoutContentLayout: ViewGroup) {
        val drawer = drawerlayout.getChildAt(1) as ViewGroup
        drawerlayout.fitsSystemWindows = true
        drawerLayoutContentLayout.fitsSystemWindows = false
        drawerLayoutContentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false


    }

    /**
     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    @Deprecated("")
    fun setColorForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA))
            } else {
                // 添加 statusBarView 到布局中
                contentLayout.addView(createStatusBarView(activity, color), 0)
            }
            // 内容布局不是 LinearLayout 时,设置padding top
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            // 设置属性
            setDrawerLayoutProperty(drawerLayout, contentLayout)
        }
    }


    /**
     * 为头部是ImageView的界面设置状态栏全透明
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 ICategoryView
     * */
    fun setTransParentForImageView(activity: Activity, needOffsetView: View) {
        setTransLucentForImageView(activity, 0, needOffsetView)
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 ICategoryView
     */

    fun setTransLucentForImageView(activity: Activity, needOffsetView: View) {

        setTransLucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }


    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 ICategoryView
     */
    private fun setTransLucentForImageView(activity: Activity, statusBarAlpha: Int, needOffsetView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForWindow(activity)
        addTranslucentView(activity, statusBarAlpha)
        if (needOffsetView != null) {
            val haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET)
            if (haveSetOffset != null && haveSetOffset as Boolean) {
                return
            }
            val layoutParams = needOffsetView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity), layoutParams.rightMargin, layoutParams.bottomMargin)
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true)//这个setTag的作用？？ todo
        }

    }


    /**
     * for which fragment 's head is Imageview, to set it statusbar to be transparent
     *
     * @param activity fragment 对应的activity
     * @param needOffsetView 需要向下偏移的ICategoryView
     * */

    fun setTranslucentForImageViewInFragment(activity: Activity, needOffsetView: View) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 ICategoryView
     */

    private fun setTranslucentForImageViewInFragment(activity: Activity, statusBarAlpha: Int, needOffsetView: View) {

        setTranslucentForImageViewInFragment(activity, statusBarAlpha, needOffsetView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity)
        }
    }

    private fun clearPreviousSetting(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView)
            val rootView = (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
                    as ViewGroup
            rootView.setPadding(0, 0, 0, 0)
        }
    }


    /**   隐藏伪状态栏 ICategoryView

    @param activity 调用的activity


     */
    fun hideFakeStatusBarView(activity: Activity) {


        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if(fakeStatusBarView != null)
        {
            fakeStatusBarView.visibility = View.GONE
        }
        val fakeTranslucentView = decorView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            fakeTranslucentView.visibility = View.GONE
        }

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun clearPrebiousSetting(activity: Activity) {

        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView)
            val rootView = (activity.findViewById<View>(android.R.id.content) as ViewGroup)
            rootView.setPadding(0, 0, 0, 0)
        }
    }


    /**
     * 使状态栏透明
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)//给系统状态栏着色第一步准备
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            activity.window.statusBarColor = Color.TRANSPARENT

        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)


        }

    }

}
