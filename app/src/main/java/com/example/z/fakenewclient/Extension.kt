package com.example.z.fakenewclient

import android.content.Context
import android.view.View
import android.widget.Toast
import android.util.Log
import android.support.design.widget.Snackbar
import android.webkit.WebView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Author: Z.King.James
 * Declarations:封装，集合各种类的扩展函数
 * Created on: 2018/10/12:17:49
 * Mail:mrzhaoxiaolin@163.com
 */
fun Context.toast(meg: String, duration: Int = Toast.LENGTH_SHORT) {

    Toast.makeText(this, meg, duration).show()
}


fun View.snackBar(meg: String, duration: Int = Snackbar.LENGTH_SHORT) {

    Snackbar.make(this, meg, duration).show()

}

fun View.snackbar(messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageRes, duration).show()
}

fun Any.Log(msg: String) {

    Log.e(this.javaClass.simpleName, msg)
}


fun WebView.load(html:String){
    this.loadDataWithBaseURL("http://ishuhui.net",html,"text/html","charset-utf-8",null)
}



fun ImageView.loadUrl(url:String)
{
    var options = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_img_defult)
        .error(R.drawable.ic_img_defult)
    Glide.with(this.context).load(url).apply(options).into(this)
}

