package com.example.z.fakenewclient.module.news.model

data class NewsBean(val ctime:String, var title:String, var description:String, var picUrl:String?, var url:String)

data class BaseBean<T>(val code:Int,val msg:String,var newslist:T)