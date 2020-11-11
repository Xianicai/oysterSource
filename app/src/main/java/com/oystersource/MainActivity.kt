package com.oystersource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.oystersource.person.view.LoginActivity
import com.oystersource.source.view.SourceFragment
import com.oystersource.person.view.PersonFragment
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.savegoldmaster.home.view.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QMUIStatusBarHelper.translucent(this)
        initViewPager()
        initTabs()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        val mHomeFragment = HomeFragment.newInstance()
        val mSourceFragment = SourceFragment.newInstance()
        val mPersonFragment = PersonFragment.newInstance()
        fragments.add(mHomeFragment)
        fragments.add(mSourceFragment)
        fragments.add(mPersonFragment)
        val adapter = BaseFragmentPagerAdapter(supportFragmentManager, fragments)
        pager.adapter = adapter
        pager.currentItem = 0
        mTabSegment.setupWithViewPager(pager, false)
    }

    private fun initTabs() {
        mTabSegment.setHasIndicator(false)
        val home =
            buildTab("首页", R.mipmap.ic_home3, R.mipmap.ic_home_selected3)
        val source = buildTab("溯源", R.mipmap.ic_source3, R.mipmap.ic_source_selected3)
        val person = buildTab("个人", R.mipmap.ic_person3, R.mipmap.ic_person_selected3)
        mTabSegment.run {
            addTab(home)
            addTab(source)
            addTab(person)
        }
        mTabSegment.notifyDataChanged()

    }

    private fun buildTab(tabTitle: String, normalIcon: Int, selectedIcon: Int): QMUITabSegment.Tab {
        val tab = QMUITabSegment.Tab(
            ContextCompat.getDrawable(this, normalIcon),
            ContextCompat.getDrawable(this, selectedIcon),
            tabTitle, false
        )
//        tab.setTextColor(
//            QMUIResHelper.getAttrColor(this, R.color.color_999999),
//            QMUIResHelper.getAttrColor(this, R.color.theme_color)
//        )
        return tab
    }

    private class BaseFragmentPagerAdapter : FragmentPagerAdapter {
        private var mDataList: List<*>? = null

        constructor(fm: FragmentManager?) : super(fm!!) {}
        constructor(fm: FragmentManager?, dataList: List<*>?) : super(fm!!) {
            mDataList = dataList
        }

        override fun getItem(position: Int): Fragment {
            return mDataList!![position] as Fragment
        }

        override fun getCount(): Int {
            return mDataList!!.size
        }
    }
}