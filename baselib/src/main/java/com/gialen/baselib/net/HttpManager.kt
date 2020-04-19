package com.gialen.baselib.net

import android.os.Build
import android.text.TextUtils
import com.gialen.baselib.BuildConfig
//import com.gialen.baselib.bean.PayOrderStatusBody
import com.gialen.baselib.data_manager.DataManager
import com.gialen.baselib.data_manager.SharedPreferencesManager
import com.gialen.baselib.net.api.ApiService
import com.gialen.baselib.net.client.RetrofitClient
//import com.gialen.baselib.net.requestbody.CartGoodsTotalFeeBean
//import com.gialen.baselib.net.requestbody.CommitOrderBean
//import com.gialen.baselib.net.requestbody.ShareCouponsRequestBody
//import com.gialen.baselib.pay.PayRequestBody
import com.gialen.baselib.util.MD5Util
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class HttpManager {


    companion object {

        private val apiService = RetrofitClient.mRetrofit.create(ApiService::class.java)


        /**
         * 执行请求操作
         *
         * @param observable
         * @param observer
         * @param <T>
        </T> */
        private fun <T> execute(observable: Observable<T>, observer: Observer<T>) {
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }


        /**
         * 封装必传参数
         * @param data 请求参数
         */
        private fun getBaseParams(data: JSONObject?): RequestBody {
            return RequestBody.create(MediaType.parse("application/json"), data.toString())

        }

        /**
         * 封装必传参数
         * @param data 请求参数
         */
        private fun getBaseParams(data: JSONArray?): RequestBody {
            return RequestBody.create(MediaType.parse("application/json"), data.toString())
        }


        /**
         * post 请求
         * @param method
         * @param data
         * @param observer
         */
        fun postNoToken(method: String, data: JSONObject, observer: Observer<JSONObject>) {
            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + "" + "|" + reqDate+ "|" +method)
            val observable = apiService.postRequest(BuildConfig.BASEURL+method, sign, reqDate, body)
            execute(observable, observer)
        }

        /**
         * post 请求
         * @param method
         * @param data
         * @param observer
         */
        fun postNoToken(method: String, data: JSONArray, observer: Observer<JSONObject>) {
            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + "" + "|" + reqDate+ "|" +method)

            val observable = apiService.postRequest(BuildConfig.BASEURL+method, sign, reqDate, body)
            execute(observable, observer)
        }


        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
        open fun postHasToken(method: String, token: String, data: JSONObject?, observer: Observer<JSONObject>) {
            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
            val observable = apiService.postRequest(BuildConfig.BASEURL+method, token, sign, reqDate, body)
            execute(observable, observer)
        }


        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
//        open fun postCommitOrder(method: String, token: String, requestBody: CommitOrderBean, observer: Observer<JSONObject>) {
//            var date = Date()
//            val format = SimpleDateFormat("yyyyMMddHHmm")
//            var reqDate = format.format(date)
//            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
//            val observable = apiService.postCommitOrder(BuildConfig.BASEURL+method, token, sign, reqDate, requestBody)
//            execute(observable, observer)
//        }

     /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
//        open fun postShareCoupons(method: String, token: String, requestBody: ShareCouponsRequestBody, observer: Observer<JSONObject>) {
//            var date = Date()
//            val format = SimpleDateFormat("yyyyMMddHHmm")
//            var reqDate = format.format(date)
//            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
//            val observable = apiService.postShareCoupons(BuildConfig.BASEURL+method, token, sign, reqDate, requestBody)
//            execute(observable, observer)
//        }

        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
//        open fun postCartGoodsTotalFee(method: String, token: String, requestBody: CartGoodsTotalFeeBean, observer: Observer<JSONObject>) {
//            var date = Date()
//            val format = SimpleDateFormat("yyyyMMddHHmm")
//            var reqDate = format.format(date)
//            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
//            val observable = apiService.postCartGoodsTotalFee(BuildConfig.BASEURL+method, token, sign, reqDate, requestBody)
//            execute(observable, observer)
//        }

        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
//        open fun postPayOrder(method: String, token: String, requestBody: PayRequestBody, observer: Observer<JSONObject>) {
//            var date = Date()
//            val format = SimpleDateFormat("yyyyMMddHHmm")
//            var reqDate = format.format(date)
//            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
//            val observable = apiService.postPayOrder(BuildConfig.BASEURL+method, token, sign, reqDate, requestBody)
//            execute(observable, observer)
//        }
 /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
//        open fun postOrderStatus(method: String, token: String, payOrderStatusBody: PayOrderStatusBody, observer: Observer<JSONObject>) {
//            var date = Date()
//            val format = SimpleDateFormat("yyyyMMddHHmm")
//            var reqDate = format.format(date)
//            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
//            val observable = apiService.postOrderStatus(BuildConfig.BASEURL+method, token, sign, reqDate, payOrderStatusBody)
//            execute(observable, observer)
//        }

        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
        open fun postHasToken(method: String, token: String, data: JSONArray, observer: Observer<JSONObject>) {
            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + token + "|" + reqDate+ "|" +method)
            val observable = apiService.postRequest(BuildConfig.BASEURL+method, token, sign, reqDate, body)
            execute(observable, observer)
        }

        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
        open fun postLogin(method: String, phone: String, data: JSONObject, observer: Observer<JSONObject>) {
            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            var sign = MD5Util.MD5("jiaomigo.gialen.com#2019|" + phone + "|" + reqDate+ "|" +method)
            val observable = apiService.postLogin(BuildConfig.BASEURL+method, sign, reqDate, body)
            execute(observable, observer)
        }

        /**
         * post 请求
         *
         * @param method
         * @param token
         * @param data
         * @param observer
         */
        open fun postBuriedPoint(page: String, eventType: Int, eventArea: String, eventObj: String, param: String, observer: Observer<JSONObject>) {
            var data = JSONObject()
            val sessionId = SharedPreferencesManager.getInstance().getString(SharedPreferencesManager.SPCommons.SESSIONID, "")
            data.put("sessionId", sessionId)
            data.put("platform", 1)
            data.put("appVersion", BuildConfig.VERSION_NAME)
            data.put("ter",  Build.MODEL)
            data.put("terv","android"+ android.os.Build.VERSION.RELEASE+" "+android.os.Build.VERSION.SDK)

            val userInfo = DataManager.getInstance().queryUserInfo()
            var token = ""
            if (userInfo != null) {
                token = userInfo.token!!
                data.put("userId", userInfo.id)
                data.put("token", token)
            }

            data.put("eventType", eventType)
            data.put("page", page)
            if (!TextUtils.isEmpty(eventArea)) {
                data.put("eventArea", eventArea)
            }
            if (!TextUtils.isEmpty(eventObj)) {
                data.put("eventObj", eventObj)
            }
            if (!TextUtils.isEmpty(param)) {
                data.put("param", param)
            }

            val body = getBaseParams(data)
            var date = Date()
            val format = SimpleDateFormat("yyyyMMddHHmm")
            var reqDate = format.format(date)
            val observable = apiService.postBuriedPoint("http://jiaomigo.gialen.com/gdata/newPoint", reqDate, body)
            execute(observable, observer)
        }


        /**
         * 上传文件
         *
         * @param token
         * @param file
         * @param observer
         */
        open fun upLoadImage(method: String, token: String, file: File, observer: Observer<JSONObject>) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            apiService.upLoadImage(method, token, body)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        /**
         * 下载文件
         */
        open fun downloadFile(url: String, observer: Observer<ResponseBody>) {
            apiService.downloadFile(url).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }



    }
}
