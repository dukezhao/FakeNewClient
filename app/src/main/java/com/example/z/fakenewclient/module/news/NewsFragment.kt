package com.example.z.fakenewclient.module.news

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.z.fakenewclient.R
import com.example.z.fakenewclient.base.BaseLazyFragment
import com.example.z.fakenewclient.module.news.model.BaseBean
import com.example.z.fakenewclient.module.news.model.NewsBean
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseLazyFragment(), INewsview, SwipeRefreshLayout.OnRefreshListener {

    var isRefresh: Boolean = false
    var mPresenter: NewsPresenter? = null

    private var newType: Int = 0

    private var page: Int = 0

    private var num: Int = 10 //一次是10条

    override fun onRefresh() {
        swl?.isRefreshing = true
        page = 0
        mPresenter?.fetchNews(newType!!, page!!, num)
    }

    //init , and init parameters
    companion object {
        fun newInstance(type: Int): NewsFragment {
            var fragment = NewsFragment()
            var bd = Bundle()//create bundle 放参数
            bd.putInt("type", type)
            fragment.arguments = bd
            return fragment
        }
    }

    override fun getContentId(): Int {
        return R.layout.fragment_news
    }

    protected var isPrepared: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepared = true//?what is ready todo viewCreated is ready ,
        lazyLoad()

    }


    override fun lazyLoad() {
        Log.d("tag", "ispre: " + isPrepared + " isvis: " + isVisiableToUser)

        if (!isVisiableToUser || !isPrepared) {
            return
        }
        Log.d("tag", "lazyload start")
        initDatas()
        initViews()
        mPresenter?.fetchNews(newType!!, page!!, num)
        isPrepared = false//why set false todo
    }

    private var totalItemCount: Int = 0
    var lastVisibleItem: Int = 0
    var visibleItemCount: Int = 0
    var firstVisibleItem: Int = 0

    private fun initViews() {
        news_rcv.layoutManager = LinearLayoutManager(activity)
        news_rcv.adapter = adapter
        news_rcv.itemAnimator?.addDuration = 0
        var mScrollerListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (!isRefresh //没刷新过
                        && totalItemCount <= lastVisibleItem + 1 //all item数量小于最后可见的item的index+1
                        && totalItemCount > visibleItemCount//all item数量>可见item数量 ？why todo
                        && visibleItemCount > 0)//可见数量>0
                    {
                        this@NewsFragment.page++
                        mPresenter?.fetchNews(newType!!, page!!, num)
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                visibleItemCount = layoutManager.childCount

            }

        }
        news_rcv.setOnScrollListener(mScrollerListener)
        adapter?.addFooters(
            LayoutInflater.from(activity).inflate(
                R.layout.item_footer,
                news_rcv,
                false
            )
        )
    }

    private val datas = ArrayList<NewsBean>()


    private var adapter: NewsAdapter? = null

    //init newsType ,mPresenter,adapter(for viewpager)
    private fun initDatas() {
        newType = arguments!!.getInt("type")
        mPresenter = NewsPresenter(this)
        adapter = NewsAdapter(datas, context!!)
        page = 0
        // adapter=NewsAdapter(datas,activity)
    }

    //control swiperefreshlayout
    override fun onNewsFetched(resp: BaseBean<List<NewsBean>>) {


        swl?.isRefreshing = false
        Log.i("TAG", "get resp:" + resp.toString())
        if (resp.code == 200) {

            Log.d("TAG", "news fetched:")
            if (page == 0) {
                Log.d("TAG", "clear data:")
                adapter?.clearData()
            }
            adapter?.addDatas(resp.newslist)

        }
    }

    override fun onNewsFetchedFailed(throwable: Throwable) {
        swl?.isRefreshing = false
        Log.i("TAG", "get resp error:${throwable.toString()}")
    }


}
