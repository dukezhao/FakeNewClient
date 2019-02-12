package com.example.modulaa.model

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/12/5:18:16
 * Mail:mrzhaoxiaolin@163.com
 */
data class CategoryResult(


        var error: Boolean,
        var result: List<ResultsBean> = emptyList()
)

data class ResultsBean(
        var _id: String,
        var createdAt: String,
        var desc: String,
        var publishedAt: String,
        var source: String,
        var type: String,
        var url: String,
        var used: Boolean,
        var who: String,
        var images: List<String> = emptyList()
)



