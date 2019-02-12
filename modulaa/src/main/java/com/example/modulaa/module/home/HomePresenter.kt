package com.example.modulaa.module.home

import com.example.modulaa.model.CategoryResult
import com.example.modulaa.model.PictureModel
import com.example.modulaa.model.ResultsBean
import com.example.modulaa.net.Network
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/3:14:28
 * Mail:mrzhaoxiaolin@163.com
 */

//变量是一个接口，mHomeView
class HomePresenter internal constructor(
        private val mHomeView: HomeContract.IHomeView
) : HomeContract.IHomePresenter {

    private var mSubscription: Subscription? = null


    private var mModels: MutableList<PictureModel>


    val bannerModel: List<PictureModel>
        get() = this.mModels

    init {
        mModels = ArrayList()
    }


    override fun getBannerData() {
        mSubscription = Network.getGankApi()
                .getCategoryData("福利", 5, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CategoryResult> {
                    override fun onError(e: Throwable?) {
                        mHomeView.showBannerFail("banner add datas failed ")
                    }

                    override fun onNext(categoryResult: CategoryResult?) {
                        if (categoryResult != null && !categoryResult.result.isEmpty()) {
                            val imgUrls = ArrayList<String>()
                            for (result: ResultsBean in categoryResult.result) {
                                if (!result.url.isEmpty()) {
                                    imgUrls.add(result.url)
                                }
                                mModels.add(PictureModel(if (result.desc.isEmpty()) "unknown" else result.desc, result.url))//para1 :index, para2: element
                            }
                            mHomeView.setBanner(imgUrls)

                        } else {
                            mHomeView.showBannerFail("banner image load failed ")
                        }
                    }

                    override fun onCompleted() {
                    }

                })


    }

    override fun subscribe() {
        getBannerData()
    }

    override fun unSubscribe() {
        //let 后边｛｝为闭包，可带参数
        mSubscription?.let {

            if (!mSubscription!!.isUnsubscribed) {
                mSubscription!!.unsubscribe()
            }
        }
    }

}