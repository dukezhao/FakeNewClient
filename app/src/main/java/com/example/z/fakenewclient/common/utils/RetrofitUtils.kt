package com.example.z.fakenewclient.common.utils

import android.util.Log
import com.example.z.fakenewclient.BuildConfig
import com.example.z.fakenewclient.base.BaseCommand
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.z.fakenewclient.common.net.MapFiled
import com.example.z.fakenewclient.common.net.NetApi
import com.example.z.fakenewclient.config.Constant
import com.example.z.fakenewclient.module.news.model.BaseBean
import com.example.z.fakenewclient.module.news.model.NewsBean
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Author: Z.King.James
 * Declarations:
 * Created on: 2018/10/10:18:00
 * Mail:mrzhaoxiaolin@163.com
 */
object RetrofitUtils {
    private var retrofit: Retrofit? = null
    private val okhttp: OkHttpClient
        get() {//customized getOkhttp method
            var builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                //customized interceptor
                builder.addInterceptor { chain: Interceptor.Chain ->
                    Log.d("retro", "request:" + chain.request())
                    chain.proceed(chain.request())

                }
            } else {
                builder.connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
            }

            return builder.build()

        }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//what's this 扩展的是对网络工作对象callWorker的自动转换，把Retrofit中执行网络请求的Call对象，转换为接口中定义的Call对象。
                // 在这里，callAdapter做的事情就是把retrofit2.Call对象适配转换为Flowable<T>对象。同样，如果现有的扩展包不能满足需要，
                // 可以继承Retrofit的接口retrofit2.CallAdapter<R,T>，自己实现CallAdapter和CallAdapterFactory。
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()
        }


        return retrofit!!//must not be null
    }

    //todo createMaps & greageListFields using



    private fun createMaps(list: List<MapFiled>): Map<String,String> {
        var map=HashMap<String,String>()
        val size=list.size
        (0 until size)
            .map { list[it] }.forEach { map.put(it.getKey(),it.getValue()) }
        return map
    }

    private fun createListFields(cmd: BaseCommand): List<MapFiled> {
        var list=ArrayList<MapFiled>()
        val fields=cmd.javaClass.declaredFields

        for (field in fields){
            var isAccess=field.isAccessible
            field.isAccessible=true
            if(field.get(cmd)!=null){
                list.add(MapFiled(field.name,field.get(cmd).toString()))
            }
            field.isAccessible=isAccess
        }
        return list
    }
    // 各种get news
    fun fetchNews(path:String,cmd:BaseCommand):Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchNews(path,createMaps(createListFields(cmd)))//(NetApi::class.java表示获取对象的java类，可能retrofit这里还是需要java类，
        // 我们是kt类，需要转换，参见https://www.kotlincn.net/docs/reference/java-interop.html
    }

    fun fetchWorldNews(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchWorldNews(createMaps(createListFields(cmd)))
    }

    fun fetchHuabian(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchHuabian(createMaps(createListFields(cmd)))
    }
    fun fetchQiWen(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchQiWen(createMaps(createListFields(cmd)))
    }
    fun fetchHealth(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchHealth(createMaps(createListFields(cmd)))
    }
    fun fetchTiyu(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchTiyu(createMaps(createListFields(cmd)))
    }
    fun fetchKeJi(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchKeJi(createMaps(createListFields(cmd)))
    }
    fun fetchTraval(cmd:BaseCommand) :Observable<BaseBean<List<NewsBean>>>{
        return getRetrofit().create(NetApi::class.java).fetchTraval(createMaps(createListFields(cmd)))
    }



}