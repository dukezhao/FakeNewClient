package com.example.z.fakenewclient.base

import android.content.Context
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.z.fakenewclient.R

/**
 * Author: Z.King.James
 * Declarations:基于老的listview 练手的baseViewHolder
 * Created on: 2018/10/10:16:44
 * Mail:mrzhaoxiaolin@163.com
 */
class BaseViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
    var mViews: SparseArray<View>? = null

    init {
        mViews = SparseArray()
    }

    private fun <T> getView(viewId: Int): T {
        var v = mViews?.get(viewId)//SparseArray feature
        if (v == null) {
            //create new view
            v = itemView.findViewById(viewId)
            mViews?.put(viewId, v)
        }
        Log.d("baseviewholder", "run next")
        return v as T
    }

    fun setText(viewId: Int, value: CharSequence) {
        var v = getView<TextView>(viewId)
        v.text = value
    }

    fun setText(viewId: Int, value: String) {
        var v = getView<TextView>(viewId)
        v.text = value
    }

    fun setImageWithUrl(viewId: Int, url: String?) {
        if (url == null) return

        var v: ImageView = getView<ImageView>(viewId) ?: return
        var options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)//replace ic_img_default
            .error(R.drawable.ic_launcher_foreground)
        Glide.with(context).load(url).apply(options).into(v)

    }

    fun setImageSource(viewId: Int, sourceId: Int) {
        var v: ImageView = getView<ImageView>(viewId) ?: return
        var options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)//replace ic_img_default
            .error(R.drawable.ic_launcher_foreground)
        Glide.with(context).load(sourceId).apply(options).into(v)
    }

    fun setOnclickListener(viewId: Int, listener: View.OnClickListener) {
        var v = getView<View>(viewId)
        v?.setOnClickListener(listener)
    }
}