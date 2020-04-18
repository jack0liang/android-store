package com.example.teststore.ui.mystore

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.teststore.R
import com.gialen.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mystore.*;

class MyStoreFragment : BaseFragment() {
    private var myStoreViewModel: MyStoreViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_mystore
    }

    override fun initView() {
        myStoreViewModel = ViewModelProviders.of(this).get(MyStoreViewModel::class.java)

        myStoreViewModel!!.text.observe(viewLifecycleOwner, Observer { s ->
            Log.d("shop", "MyStoreViewModel: my store on change")
            text_mystore.text = s
        })
    }
}