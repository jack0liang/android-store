package com.gialen.baselib.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gialen.baselib.R
//import com.gialen.baselib.base.DataBingBaseActivity
import com.gialen.baselib.constant.UrlConstant
import com.gialen.baselib.data_manager.DataManager
//import com.gialen.baselib.databinding.ActivityLoginPhoneBinding
import com.gialen.baselib.live_data_bus.BaseEvent
import com.gialen.baselib.live_data_bus.EventCodeConstant
import com.gialen.baselib.live_data_bus.LiveDataBus
import com.gialen.baselib.net.response.ResultJSONObjectObserver

import com.gialen.baselib.viewmodel.VerficationCodeViewMode
import com.gialen.baselib.widget.loading.LoadingDialog
import kotlinx.android.synthetic.main.activity_login_phone.*
import org.json.JSONObject

@Route(path = "/base/login_phone")
class LoginPhoneActivity : AppCompatActivity(), View.OnClickListener {

    var loadingDialog: LoadingDialog?=null
    protected open fun showLoading(){
        if (loadingDialog!=null) {
            loadingDialog?.show()
        }
    }
    protected open fun hideLoading(){
        if (loadingDialog!=null){
            loadingDialog?.hide()
        }
    }

    fun getLayoutId(): Int = R.layout.activity_login_phone
    var isAgree: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        observePhoneInput()
        ll_login_phone.setOnClickListener(this)
        img_gialen_close.setOnClickListener(this)
        ll_agree.setOnClickListener(this)
        tv_gialen_agreement.setOnClickListener(this)
        tv_privacy_agreement.setOnClickListener(this)

        LiveDataBus.get().with("eventCode", BaseEvent::class.java)!!.observe(this, Observer {
            if (it.eventCode == EventCodeConstant.isLogin) {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo != null) {
                    if (userInfo.userLevel == 1) {
                        finish()
                    } else {
                        ARouter.getInstance().build("/store/main").navigation()
                        finish()
                    }
                }
            }
        })
    }

    /**
     * 监听输入手机号
     */
    private fun observePhoneInput() {
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 11) {
                    ll_login_phone.isEnabled = true
                    ll_login_phone.background = getDrawable(R.drawable.btn_enable_bg)
                } else {
                    ll_login_phone.isEnabled = false
                    ll_login_phone.background = getDrawable(R.drawable.btn_unenable_bg)
                }
            }
        })
    }

    protected val toast: Toast by lazy {
        Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }
    fun showToast(msg: String) {
        toast.setText(msg)
        toast.show();
    }
    /**
     * 获取验证码
     */
    private fun getVerificationCode() {
        var phone = et_phone.text.toString().trim()

            if (isAgree) {
                showLoading()
                ll_login_phone.isEnabled = false
                val viewModel = VerficationCodeViewMode()//ViewModelProviders.of(this).get(VerficationCodeViewMode::class.java)
                viewModel.getVerficationCode( phone,object : ResultJSONObjectObserver(lifecycle) {
                    override fun onSuccess(result: JSONObject?) {
                        hideLoading()
                        ll_login_phone.isEnabled = true
                        ARouter.getInstance().build("/base/login_phone_code").withString("phone", phone).navigation()
                    }

                    override fun onFail(msg: String?,code :Int) {
                        hideLoading()
                        ll_login_phone.isEnabled = true
                        showToast(msg!!)
                    }
                })

            } else {
                showToast("注册登录必须同意《娇兰佳人服务协议》和 《隐私协议》")
            }

    }

    /**
     * 隐藏键盘
     */
    open fun hideKeyBoard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
    /**
     * 点击事件
     */
    override fun onClick(view: View?) {
        hideKeyBoard(view!!)

        when (view?.id) {
            R.id.img_gialen_close -> {
                setResult(1234)
                finish()
            }
            R.id.ll_login_phone -> getVerificationCode()
            R.id.ll_agree -> {
                if (!isAgree) {
                    isAgree = true
                    iv_agree.setImageResource(R.mipmap.login_register_rb_s)
                } else {
                    isAgree = false
                    iv_agree.setImageResource(R.mipmap.login_register_rb_n)
                }
            }
            R.id.tv_gialen_agreement -> {
                var bundle = Bundle()
                bundle.putString("title", "娇兰佳人用户协议")
                bundle.putString("url", UrlConstant.userProtocol)
                ARouter.getInstance().build("/base/noshare_webview").with(bundle).navigation()
            }
            R.id.tv_privacy_agreement -> {
                var bundle = Bundle()
                bundle.putString("title", "隐私协议")
                bundle.putString("url", UrlConstant.privateProtocol)
                ARouter.getInstance().build("/base/noshare_webview").with(bundle).navigation()
            }

        }
    }
}
