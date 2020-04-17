package com.gialen.baselib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gialen.baselib.widget.loading.LoadingDialog


abstract class BaseFragment : Fragment() {

    private var mRootView: View? = null


    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
    }

    protected var mContext: Activity? = null
    protected var rootView: View? = null
    protected val toast: Toast by lazy {
        Toast.makeText(activity, "", Toast.LENGTH_SHORT);
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null)
        }
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null)
            mContext = activity
        } else {
            val parent = rootView?.parent as ViewGroup?
            parent?.removeView(rootView)
        }
        activity!!.window.decorView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                activity!!.window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                lazzyLoad()
            }
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(loadingDialog==null){
            loadingDialog = LoadingDialog(context!!)
        }
        initView()
    }

    protected open fun lazzyLoad() {

    }


    var loadingDialog: LoadingDialog?=null


    protected open fun showLoading() {
        if (!activity!!.isDestroyed) {
            loadingDialog?.show()
        }
    }

    protected open fun hideLoading() {
        if (!activity!!.isDestroyed&&loadingDialog != null) {
            loadingDialog?.hide()
        }
    }
    /**
     * 加载布局
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()


    fun showToast(msg: String) {
        toast.setText(msg)
            toast.show();
    }

    fun cancelToast() {
            toast.cancel();
    }


    /**
     * 隐藏键盘
     */
    open fun hideKeyBoard(v: View) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * 显示键盘
     */
    open fun showKeyBoard(v: View) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, 0)
    }

}


