package com.example.modulaa.net

import com.example.modulaa.model.CategoryResult
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


interface GankApi {


    /**
     * depend on category to get Android ,ios &etc gank Data
     * @param category  类别
     * @param count     条目数目
     * @param page      页数
     * */

    @GET("data/{category}/{count}/{page}")

    fun getCategoryData(@Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Observable<CategoryResult>
}
