package com.example.modulaa.module.home

import com.example.modulaa.Base.BasePresenter
import com.example.modulaa.Base.BaseView

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/3:09:52
 * Mail:mrzhaoxiaolin@163.com
 */
interface HomeContract {

    interface IHomeView : BaseView {
        fun showBannerFail(failMessage: String)
        fun setBanner(imageUrls: List<String>)
    }

    interface IHomePresenter : BasePresenter {
        /**
         * get banner circle AD picture ' Data
         * */
        fun getBannerData()

    }
}