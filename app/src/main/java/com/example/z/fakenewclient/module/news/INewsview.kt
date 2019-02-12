package com.example.z.fakenewclient.module.news

import com.example.z.fakenewclient.module.news.model.BaseBean
import com.example.z.fakenewclient.module.news.model.NewsBean

interface INewsview {
fun onNewsFetched(resp: BaseBean<List<NewsBean>>)
    fun onNewsFetchedFailed(throwable: Throwable)
}
