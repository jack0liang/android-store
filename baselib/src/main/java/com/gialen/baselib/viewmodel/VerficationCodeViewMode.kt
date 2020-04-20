package com.gialen.baselib.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.gialen.baselib.net.response.ResultJSONObjectObserver
import com.gialen.baselib.net.HttpManager
import org.json.JSONObject


open class VerficationCodeViewMode : ViewModel() {
     open fun getVerficationCode(phone: String,jsonObjectObserver: ResultJSONObjectObserver) {
        var data = JSONObject()
        data.put("phone", phone)
        HttpManager.postHasToken("app/req/user.getLoginRegistryVerification", "", data, jsonObjectObserver  )
    }

      open fun checkVerificationCode(token: String,phone: String,code: String,jsonObjectObserver: ResultJSONObjectObserver) {
          var data = JSONObject()
          data.put("phone", phone)
          data.put("code", code)
          HttpManager.postHasToken("app/req/UserService.checkVerificationCode",token,data,jsonObjectObserver)
    }


}

