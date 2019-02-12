package com.example.modulaa.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/5:18:01
 * Mail:mrzhaoxiaolin@163.com
 */
object Network {

    private var gankApi: GankApi? = null

    private var okHttpClient = OkHttpClient()


    fun getGankApi(): GankApi {

        if (gankApi == null) {
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            gankApi = retrofit.create(GankApi::class.java)
        }
        return gankApi!!
    }


}