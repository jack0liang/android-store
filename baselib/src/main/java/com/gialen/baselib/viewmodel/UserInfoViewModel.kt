package com.gialen.baselib.viewmodel

import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.gialen.baselib.data_manager.DataManager
import com.gialen.baselib.net.response.ResultJSONObjectObserver
import com.gialen.baselib.net.HttpManager
//import com.gialen.baselib.net.response.ResultJSONArrayObserver
import org.json.JSONArray
import org.json.JSONObject

class UserInfoViewModel: ViewModel(){

    /**
     * 订单角标
     */
    fun getOrderStatusCount( observer: ResultJSONObjectObserver) {
        val userInfo = DataManager.getInstance().queryUserInfo()
        var data = JSONObject()
        if (userInfo != null) {
            HttpManager.postHasToken("app/req/order.getOrderStatusCount", userInfo.token!!, data, observer)
        }else{
            ARouter.getInstance().build("/base/login_phone").navigation()
        }
    }


    /**
     * 更新个人信息
     */
    fun updateUserInfo( headUrl:String?, nickname:String?,birthday:String?, sex:String?, username:String?, userId:String?, password:String?, jsonObjectObserver: ResultJSONObjectObserver) {
        val userInfo= DataManager.getInstance().queryUserInfo()
        if (userInfo!=null){
            var data= JSONObject()
            if (!TextUtils.isEmpty(headUrl)){
                data.put("userHeadPic",headUrl)
            }
            if (!TextUtils.isEmpty(nickname)){
                data.put("nickname",nickname)
            }
            if (!TextUtils.isEmpty(birthday)){
                data.put("birthday",birthday)
            }
            if (!TextUtils.isEmpty(username)){
                data.put("username",username)
            }
            if (!TextUtils.isEmpty(password)){
                data.put("password",password)
            }
            if (!TextUtils.isEmpty(userId)){
                data.put("userId",userId)
            }
            if (!TextUtils.isEmpty(sex)){
                    data.put("sex",sex)
            }
            HttpManager.postHasToken("app/req/UserService.updateUserInfo",userInfo.token!!,data,jsonObjectObserver)
        }
    }

    /**
     * 我的/我的店获取收益数据等
     */
    open fun getUserAchievementById(token: String?,jsonObjectObserver: ResultJSONObjectObserver) {
        var data = JSONObject()
        HttpManager.postHasToken("app/req/UserService.getUserAchievementById", token!!, data, jsonObjectObserver  )
    }

    /**
     * 获取个人信息
     */
    open fun getUserInfo(token: String?,data : JSONObject,jsonObjectObserver: ResultJSONObjectObserver) {
        var jsonArray=JSONArray()
        var jsonObject= JSONObject()
        jsonObject.put("name","USER_LEVEL")
        jsonArray.put(jsonObject)
        data.put("extParams",jsonArray)
        HttpManager.postHasToken("app/req/UserService.getUserById", token!!, data, jsonObjectObserver  )
    }

}
