package com.example.z.fakenewclient.module.home

import android.support.v4.app.Fragment
import android.util.Log
import com.example.z.fakenewclient.module.news.NewsFragment

class MainActPresenter(private val view: MainActivityView) {


    //add all fragment from fragment list to viewpager
    fun fetchFragments(num:Int){
        var fragments=ArrayList<Fragment>()
        var i=0
        while (i<num){
            fragments.add(NewsFragment.newInstance(i))
            i++
        }
        Log.d("MainActivitypresenter","fragments'size "+fragments.size)
        view.onFragmentFetched(fragments)//mainActivity attach the fragments
    }
}
