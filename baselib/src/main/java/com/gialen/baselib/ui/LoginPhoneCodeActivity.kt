package com.gialen.baselib.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSON
import com.gialen.baselib.BuildConfig
import com.gialen.baselib.R
import com.gialen.baselib.base.DataBingBaseActivity
import com.gialen.baselib.bean.db_bean.UserInfo
import com.gialen.baselib.data_manager.DataManager
import com.gialen.baselib.data_manager.SharedPreferencesManager
import com.gialen.baselib.live_data_bus.BaseEvent
import com.gialen.baselib.live_data_bus.EventCodeConstant
import com.gialen.baselib.live_data_bus.LiveDataBus
import com.gialen.baselib.net.response.ResultJSONObjectObserver
import com.gialen.baselib.net.HttpManager
import com.gialen.baselib.util.CountDownTimerUtils
import com.gialen.baselib.viewmodel.VerficationCodeViewMode

import kotlinx.android.synthetic.main.activity_login_phone_code.*
import kotlinx.android.synthetic.main.activity_login_phone_code.ll_login_phone
import org.json.JSONObject

@Route(path = "/base/login_phone_code")
class LoginPhoneCodeActivity : DataBingBaseActivity(), View.OnClickListener {

    var phone: String? = ""
    lateinit var countDownTimerUtils: CountDownTimerUtils;


    override fun getLayoutId(): Int {
        return R.layout.activity_login_phone_code
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iv_back.setOnClickListener(this)
        tv_get_check_code.setOnClickListener(this)
        ll_login_phone.setOnClickListener(this)
        tv_cannt_get_code.setOnClickListener(this)

        code_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 6) {
                    ll_login_phone.isEnabled = true
                    ll_login_phone.background = getDrawable(R.drawable.btn_enable_bg)
                } else {
                    ll_login_phone.isEnabled = false
                    ll_login_phone.background = getDrawable(R.drawable.btn_unenable_bg)
                }
            }

        })

        phone = intent.getStringExtra("phone")

        var text = phone?.substring(0, 3) + "****" + phone?.substring(7, 11)
        tv_desc.text = "验证码已发送至" + text + ",请在下方输入框输入6位数字验证码"

        showKeyBoard(li_all)
        countDownTimerUtils = CountDownTimerUtils(tv_get_check_code, 60000, 1000)
        countDownTimerUtils.start()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_cannt_get_code -> {
                showTipDialog()
            }
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_get_check_code -> {
                getCheckCode()
            }
            R.id.ll_login_phone -> {
                postLogin()
            }


        }
    }

    /**
     * 获取验证码
     */
    private fun getCheckCode() {
        tv_get_check_code.isEnabled = false
        countDownTimerUtils.start()
        showLoading()
        val viewModel = VerficationCodeViewMode()
        viewModel.getVerficationCode(phone!!, object : ResultJSONObjectObserver(lifecycle) {
            override fun onSuccess(result: JSONObject?) {
                hideLoading()
                tv_get_check_code.isEnabled = true
            }

            override fun onFail(msg: String?,code :Int) {
                hideLoading()
                tv_get_check_code.isEnabled = true
            }
        })

    }


    override fun onResume() {
        super.onResume()
        getWindow().getDecorView().postDelayed(Runnable() {
            showKeyBoard(code_et)
            code_et.requestFocus()
            code_et.performClick()
        }, 500);
    }

    /**
     * 收不到验证码提示
     */
    fun showTipDialog() {
        var content = "1、请检查手机号手机号是否输入正确；\n2、如果安装了360卫士、安全管家、QQ管家等软件，请进行软件查询拦截记录，并将娇兰佳人短信设为信任后重试；\n3 、 请您清除手机缓存后重新获取 ；\n4、请确人是否退订过（10690329016173）之类的短信，如果是，请联系运营商进行解除退订；\n 5、如果以上办法无法解决，请联系娇兰佳人客服。"
        showToast("收不到验证码？" + "\n" + content)
//        val tipsDialog = TipsDialog(LoginPhoneCodeActivity@ this)
//        tipsDialog.setContent(, content, "关闭")
//        tipsDialog.show()
    }


    /**
     * 登录
     */
    fun postLogin() {
        hideKeyBoard(code_et)
        showLoading()
        var data = JSONObject()
        try {
            data.put("phone", phone)
            if (!TextUtils.isEmpty(code_et.text.toString().trim())) {
                data.put("verificationCode", code_et.text.toString().trim())
                HttpManager.postLogin("app/login", phone!!, data, object : ResultJSONObjectObserver(lifecycle) {
                    override fun onSuccess(result: JSONObject) {
                        hideLoading()
                        val userInfo = JSON.parseObject(result.toString(), UserInfo::class.java)
                        LiveDataBus.get().with("eventCode", BaseEvent::class.java)!!.postValue(BaseEvent(EventCodeConstant.isLogin))
                        SharedPreferencesManager.getInstance().saveString(SharedPreferencesManager.SPCommons.TOKEN, userInfo.token)
                        DataManager.getInstance().insertUserInfo(userInfo)

                        finish()
                    }

                    override fun onFail(msg: String?,code :Int) {
                        hideLoading()
                        showToast(msg!!)
                    }
                })
            }

        } catch (e: Exception) {
            if(BuildConfig.DEBUG)
                Log.e("gialen",e.toString())
        }


    }
}
