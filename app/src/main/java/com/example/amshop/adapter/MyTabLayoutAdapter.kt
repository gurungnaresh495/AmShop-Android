package com.example.amshop.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.amshop.fragment.SubCategoryFragment
import com.example.amshop.model.SubCategory

class MyTabLayoutAdapter(var fm: FragmentManager): FragmentPagerAdapter(fm) {

    var fragmentList = ArrayList<Fragment>()
    var titleList = ArrayList<String>()

    override fun getCount(): Int {
       return titleList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(subCat: SubCategory)
    {
        fragmentList.add(SubCategoryFragment.newInstance(subCat.subId))
        titleList.add(subCat.subName)
        notifyDataSetChanged()
    }

}