package com.example.z.fakenewclient.module.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.z.fakenewclient.R
import kotlinx.android.synthetic.main.activity_web_layout.*


class WebViewAct : AppCompatActivity() {
    companion object {

        //start webAct with extraData intent
        fun start(context: Context, data: String) {
            var intent = Intent(context, WebViewAct::class.java)
            //todo
            intent.putExtra("url", data)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_layout)
        val url = intent.getStringExtra("url")
        val webSettings = web_view.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.setAppCacheEnabled(true)
        web_view.loadUrl(url)
        web_view.setWebViewClient(Client())
    }


    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            rl_content.removeView(web_view)
            web_view.removeAllViews()
            web_view.destroy()
        }
    }

    inner class Client:WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String?
        ): Boolean {

            if(url!=null)
            {
                view.loadUrl(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            pb_bar?.visibility= View.GONE
        }
    }
}
