package com.example.z.fakenewclient.module.news

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.z.fakenewclient.R
import com.example.z.fakenewclient.base.BaseViewHolder
import com.example.z.fakenewclient.module.news.model.NewsBean
import com.example.z.fakenewclient.module.web.WebViewAct


class NewsAdapter(var datas: ArrayList<NewsBean>, var context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var footers: SparseArray<View>
    private var headers: SparseArray<View>
    val baseItemFooter = 10001
    val baseItemHeader = 20001;

    private val TAG:String="NewsAdapter"


    init {
        footers = SparseArray()
        headers = SparseArray()
    }


    //
    fun addDatas(datas: List<NewsBean>){
        if(datas!=null)
        {
            this.datas.addAll(datas)
            notifyItemRangeChanged(this.datas.size-datas.size,datas.size)
        }
    }

    fun addheaders(view:View){
        headers?.put(baseItemFooter+headers.size(),view)//why todo
    }


    fun addFooters(view:View) {
        footers?.put(baseItemFooter + footers.size(), view)

    }

    fun clearData(){
        datas?.clear()
        notifyDataSetChanged()
    }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (headers?.get(viewType) != null) {
            return BaseViewHolder(headers.get(viewType), context)//返回头部
        } else if (footers?.get(viewType) != null) {//返回尾部
            return BaseViewHolder(footers.get(viewType), context)
        }
        var v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return BaseViewHolder(v, context)//返回一般item
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //header & footer not action
        if (isHeaderView(position)) {
            return
        } else if (isFooterView(position)) {
            return
        }
        holder.setText(R.id.news_title, datas[position].title)
        holder.setText(R.id.news_time, datas[position].ctime)
        holder?.setImageWithUrl(R.id.news_head_img, datas[position].picUrl)
        holder?.setOnclickListener(R.id.news_layout, View.OnClickListener {
            WebViewAct.start(context, datas[position].url)
        })

    }

    override fun getItemCount(): Int {
        return datas.size + headers!!.size() + footers!!.size()
    }


    private fun isHeaderView(position: Int): Boolean {
        return position < headers.size()
    }

    private fun isFooterView(position: Int): Boolean {
        return position >= headers.size() + datas.size

    }
    override fun getItemViewType(position: Int): Int {

        if(isFooterView(position))
        {
            Log.i(TAG, "keyAt's index is : $position-${datas.size}-${headers.size()}" )
            return footers.keyAt(position-datas.size-headers.size())//why ?todo log it


        }
        else if(isHeaderView(position)){
            return headers.keyAt(position)
        }
        return super.getItemViewType(position)
    }

}
