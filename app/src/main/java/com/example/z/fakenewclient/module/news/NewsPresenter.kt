package com.example.z.fakenewclient.module.news

import com.example.z.fakenewclient.base.BaseCommand
import com.example.z.fakenewclient.base.BasePresenter
import com.example.z.fakenewclient.common.utils.RetrofitUtils
import com.example.z.fakenewclient.config.Constant
import com.example.z.fakenewclient.module.news.model.NewsCommand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsPresenter(val view: INewsview?) : BasePresenter() {
    fun fetchNews(newType: Int, page: Int, num: Int) {
        val cmd = NewsCommand(page, num)
        RetrofitUtils.fetchNews(Constant.URLS[newType], cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })
    }

    fun fetchWorldNews(cmd: BaseCommand) {
        RetrofitUtils.fetchWorldNews(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })
    }


    fun fetchTravel(cmd: BaseCommand) {
        RetrofitUtils.fetchTraval(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })

    }

    fun fetchTiYu(cmd: BaseCommand) {
        RetrofitUtils.fetchTiyu(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })

    }

    fun fetchQiwen(cmd: BaseCommand) {
        RetrofitUtils.fetchQiWen(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })
    }

    fun fetchKeji(cmd: BaseCommand) {
        RetrofitUtils.fetchKeJi(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })

    }

    fun fetchHuaBian(cmd: BaseCommand) {
        RetrofitUtils.fetchHuabian(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })

    }

    fun fetchHealth(cmd: BaseCommand) {
        RetrofitUtils.fetchHealth(cmd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })

    }
}
