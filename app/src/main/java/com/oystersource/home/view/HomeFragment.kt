package com.savegoldmaster.home.view


import android.view.View
import com.oystersource.R
import com.oystersource.base.view.BaseMVPFragment
import com.oystersource.home.view.AddSeedActivity
import com.oystersource.home.view.AddFarmerActivity
import com.oystersource.home.view.BreedActivity
import com.oystersource.home.view.RecoveryActivity
import com.oystersource.person.model.bean.LoginBean
import com.oystersource.person.presenter.LoginPresenterImpl
import com.oystersource.person.presenter.contract.LoginContract
import com.oystersource.person.presenter.contract.SourceContract
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMVPFragment<LoginPresenterImpl>(), LoginContract.LoginView, View.OnClickListener {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var listBean: ArrayList<Any> = ArrayList()
    private var presenterImpl: LoginPresenterImpl? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View?) {
        mLayoutAddOyster.setOnClickListener(this)
        mLayoutAddFarmer.setOnClickListener(this)
        mLayoutBreed.setOnClickListener(this)
        mLayoutCollect.setOnClickListener(this)
    }

    override fun createPresenter(): LoginPresenterImpl {
        presenterImpl = LoginPresenterImpl()
        return presenterImpl as LoginPresenterImpl
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mLayoutAddOyster -> {
                AddSeedActivity.start(activity)
            }
            R.id.mLayoutAddFarmer -> {
                AddFarmerActivity.start(activity)
            }
            R.id.mLayoutBreed -> {
                BreedActivity.start(activity)
            }
            R.id.mLayoutCollect -> {
                RecoveryActivity.start(activity)
            }

        }
    }

    override fun login(bean: LoginBean?) {

    }

}
