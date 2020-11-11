package com.oystersource.person.view

import android.os.Environment
import android.view.View
import com.oystersource.BuildConfig
import com.oystersource.R
import com.oystersource.base.view.BaseMVPFragment
import com.oystersource.person.model.bean.UserBean
import com.oystersource.person.presenter.PersonPresenterImpl
import com.oystersource.person.presenter.contract.PersonContract
import com.oystersource.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_person.*
import java.io.File

class PersonFragment : BaseMVPFragment<PersonPresenterImpl>(), PersonContract.UserView {
    companion object {
        fun newInstance(): PersonFragment {
            return PersonFragment()
        }
    }


    private var personPresenterImpl: PersonPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_person
    }

    override fun initView(view: View?) {
        personPresenterImpl?.getUserDetail()
        mLayoutQuit.setOnClickListener {
            val helper = SharedPreferencesHelper(activity, "LoginBean")
            helper.clear()
            activity?.let { it1 -> LoginActivity.start(it1) }
            activity?.finish()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            personPresenterImpl?.getUserDetail()
        }
    }

    private fun showDeveloper(): Boolean {
        val filePath = Environment.getExternalStorageDirectory().canonicalPath + "/CunJinTong"
        return BuildConfig.DEBUG || File(filePath, "cjt_debug").exists()
    }

    override fun createPresenter(): PersonPresenterImpl {
        personPresenterImpl = PersonPresenterImpl()
        return personPresenterImpl as PersonPresenterImpl
    }

    override fun getUserDetail(userBean: UserBean?) {
        userBean ?: return
        val surname = userBean.data.name?.substring(0, 1)
        val name = userBean.data.name?.length?.let { userBean.data.name?.substring(1, it) }
        if (surname != null) {
            mTvSurname.setText(surname)
        }
        mTvName.text = name
        mLayoutQR.text = userBean.data.role
        mTvAccount.text = userBean.data.phone
        mTvPassword.text = "******"
    }
}