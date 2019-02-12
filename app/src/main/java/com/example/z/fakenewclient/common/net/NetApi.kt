package com.example.z.fakenewclient.common.net

import com.example.z.fakenewclient.module.news.model.BaseBean
import com.example.z.fakenewclient.module.news.model.NewsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:18:36
 * Mail:mrzhaoxiaolin@163.com
 */
interface NetApi {

    @GET("{path}")
    fun fetchNews(@Path("path") path: String, @QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("world/")
    fun fetchWorldNews(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("huabian/")
    fun fetchHuabian(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("qiwen/")
    fun fetchQiWen(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("health/")
    fun fetchHealth(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("tiyu/")
    fun fetchTiyu(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("keji/")
    fun fetchKeJi(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

    @GET("travel/")
    fun fetchTraval(@QueryMap maps: Map<String, String>): Observable<BaseBean<List<NewsBean>>>

}