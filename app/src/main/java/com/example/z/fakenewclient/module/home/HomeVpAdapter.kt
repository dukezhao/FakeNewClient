package com.example.z.fakenewclient.module.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class HomeVpAdapter(val fm: FragmentManager, val fragments: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {
    var titles = arrayOf("世界", "花边", "奇闻", "健康", "体育", "科技", "旅游")
    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence = titles[position]
}
