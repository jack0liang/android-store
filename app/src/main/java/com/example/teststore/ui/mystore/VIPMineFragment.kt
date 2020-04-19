package com.example.teststore.ui.mystore

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.gialen.baselib.BuildConfig
import com.gialen.baselib.base.BaseFragment
import com.gialen.baselib.bean.user_info.UserAccountBean
//import com.gialen.baselib.jpush.JPushTagAliasOperatorHelper
import com.gialen.baselib.live_data_bus.BaseEvent
import com.gialen.baselib.live_data_bus.EventCodeConstant
import com.gialen.baselib.live_data_bus.LiveDataBus
import com.gialen.baselib.net.HttpManager
import com.gialen.baselib.net.response.ResultJSONObjectObserver
import com.gialen.baselib.util.GlideCircleTransform
import com.gialen.baselib.util.ImageLoader
import com.gialen.baselib.util.Logger
import com.gialen.baselib.viewmodel.UserInfoViewModel

import com.gialen.vip.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tencent.bugly.Bugly
import kotlinx.android.synthetic.main.vip_mine_fragment.*
import org.json.JSONObject
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView

class VIPMineFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.vip_mine_fragment;
    }
    var page = 1
     var tags:String=""
    lateinit var badge1: Badge
    lateinit var badge2: Badge
    lateinit var badge3: Badge
    lateinit var view1: View
    lateinit var view2: View
    lateinit var iv_head: ImageView
    lateinit var iv_message: ImageView
    lateinit var iv_connnet_kf: ImageView
    lateinit var ll_wait_pay: LinearLayout
    lateinit var ll_wait_send: LinearLayout
    lateinit var ll_deliver: LinearLayout
    lateinit var ll_user_info: LinearLayout
    lateinit var ll_invate: LinearLayout
    lateinit var ll_jiaobi: LinearLayout
    lateinit var ll_coupin: LinearLayout
    lateinit var ll_service: LinearLayout
    lateinit var rl_accept_address: RelativeLayout
    lateinit var rl_order_center: RelativeLayout
    lateinit var re_setting: RelativeLayout
    lateinit var rl_help: RelativeLayout
    lateinit var tv_coin: TextView
    lateinit var tv_coupon_num: TextView
    lateinit var tv_name: TextView
    lateinit var tv_phone: TextView


    private lateinit var goodsListAdapter: GoodsListAdapter
    private var list = ArrayList<RecommendProductBean>()

    override fun initView() {
        viewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        view1 = LayoutInflater.from(context).inflate(R.layout.view_vipminefragment_header1, null, false)
        view2 = LayoutInflater.from(context).inflate(R.layout.view_vipminefragment_header2, null, false)
        tv_name = view1.findViewById<TextView>(R.id.tv_name)
        tv_phone = view1.findViewById<TextView>(R.id.tv_phone)
        tv_coin = view1.findViewById<TextView>(R.id.tv_coin)
        tv_coupon_num = view1.findViewById<TextView>(R.id.tv_coupon_num)
        iv_head = view1.findViewById<ImageView>(R.id.iv_head)
        iv_message = view1.findViewById<ImageView>(R.id.iv_message)
        iv_connnet_kf = view1.findViewById<ImageView>(R.id.iv_connnet_kf)
        ll_wait_pay = view1.findViewById<LinearLayout>(R.id.ll_wait_pay)
        ll_wait_send = view1.findViewById<LinearLayout>(R.id.ll_wait_send)
        ll_deliver = view1.findViewById<LinearLayout>(R.id.ll_deliver)
        ll_user_info = view1.findViewById<LinearLayout>(R.id.ll_user_info)
        ll_invate = view1.findViewById<LinearLayout>(R.id.ll_invate)
        ll_jiaobi = view1.findViewById<LinearLayout>(R.id.ll_jiaobi)
        ll_coupin = view1.findViewById<LinearLayout>(R.id.ll_coupin)
        ll_service = view1.findViewById<LinearLayout>(R.id.ll_service)
        rl_accept_address  =view1.findViewById<RelativeLayout>(R.id.rl_accept_address)
        rl_order_center  =view1.findViewById<RelativeLayout>(R.id.rl_order_center)
        rl_help  =view1.findViewById<RelativeLayout>(R.id.rl_help)
        re_setting  =view1.findViewById<RelativeLayout>(R.id.re_setting)
        iv_message.setOnClickListener(this)
        iv_connnet_kf.setOnClickListener(this)
        ll_user_info.setOnClickListener(this)
        ll_invate.setOnClickListener(this)
        ll_jiaobi.setOnClickListener(this)
        ll_coupin.setOnClickListener(this)
        ll_wait_pay.setOnClickListener(this)
        ll_wait_send.setOnClickListener(this)
        ll_deliver.setOnClickListener(this)
        ll_service.setOnClickListener(this)
        rl_order_center.setOnClickListener(this)
        rl_accept_address.setOnClickListener(this)
        rl_help.setOnClickListener(this)
        re_setting.setOnClickListener(this)
        badge1 = QBadgeView(context).bindTarget(ll_wait_pay).setBadgeGravity(Gravity.TOP or Gravity.END).setGravityOffset(20f, 0f, true).setBadgeTextColor(Color.WHITE).setBadgeBackgroundColor(Color.RED).stroke(Color.parseColor("#ffffff"), 1f, true)
        badge2 = QBadgeView(context).bindTarget(ll_wait_send).setBadgeGravity(Gravity.TOP or Gravity.END).setGravityOffset(20f, 0f, true).setBadgeTextColor(Color.WHITE).setBadgeBackgroundColor(Color.RED).stroke(Color.parseColor("#ffffff"), 1f, true)
        badge3 = QBadgeView(context).bindTarget(ll_deliver).setBadgeGravity(Gravity.TOP or Gravity.END).setGravityOffset(20f, 0f, true).setBadgeTextColor(Color.WHITE).setBadgeBackgroundColor(Color.RED).stroke(Color.parseColor("#ffffff"), 1f, true)

        recycler_view.layoutManager = GridLayoutManager(context,2)
        recycler_view.addHeaderView(view1)
        val userInfo = DataManager.getInstance().queryUserInfo()
        if (userInfo != null) {
            recycler_view.addHeaderView(view2)
        }
        goodsListAdapter = GoodsListAdapter(context!!, false)
        recycler_view.adapter = goodsListAdapter

        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                initData()
                refresh()
            }
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                loadMore()
            }
        })

        LiveDataBus.get().with("eventCode", BaseEvent::class.java)!!.observe(this, Observer {
            if (it.eventCode == EventCodeConstant.isLogin) {
                updateUserInfo()
                recycler_view.addHeaderView(view2)
                refresh()
            } else if (it.eventCode == EventCodeConstant.updateUserInfo) {
                updateUserInfo()
            }
        })
        initData()
        refresh()
    }


    private fun loadMore() {
        val userInfo = DataManager.getInstance().queryUserInfo()
        if (userInfo != null) {
            var data = JSONObject()
            ++page
            data.put("platform", 1)
            data.put("terminal", 2)
            data.put("page", page)
            data.put("limit", 6)
            HttpManager.postHasToken("app/req/recommend.productOrderRecommendV1", userInfo.token, data, object : ResultJSONObjectObserver(lifecycle) {
                override fun onSuccess(result: JSONObject?) {
                    val jsonArray = result!!.getJSONArray("list")
                    if (jsonArray.length() == 0) {
                        refreshLayout.finishLoadMore(200,true,true)
                    } else {
                        val array = JSONArray.parseArray(jsonArray.toString(), RecommendProductBean::class.java)
                        list.addAll(array)
                        goodsListAdapter.setList(list)
                        refreshLayout.finishLoadMore(200,true,false)

                    }

                }

                override fun onFail(msg: String?,code :Int) {
                    showToast(msg!!)
                    refreshLayout.finishLoadMore(false)
                }
            })
        }else{
            refreshLayout.finishLoadMore(false)

        }

    }

    private fun refresh() {
        val userInfo = DataManager.getInstance().queryUserInfo()
        if (userInfo != null) {
            var data = JSONObject()
            page = 1
            data.put("platform", 1)
            data.put("terminal", 2)
            data.put("page", page)
            data.put("limit", 6)
            HttpManager.postHasToken("app/req/recommend.productOrderRecommendV1", userInfo.token, data, object : ResultJSONObjectObserver(lifecycle) {
                override fun onSuccess(result: JSONObject?) {
                    val jsonArray = result!!.getJSONArray("list")
                    if (jsonArray.length() == 0) {
                        recycler_view.removeHeaderView(view2)
                    } else {
                        val array = JSONArray.parseArray(jsonArray.toString(), RecommendProductBean::class.java)
                        list.clear()
                        list.addAll(array)
                        goodsListAdapter.setList(list)
                    }
                    refreshLayout.finishRefresh()
                }

                override fun onFail(msg: String?,code :Int) {
                    showToast(msg!!)
                    refreshLayout.finishRefresh()
                }
            })
        }else{
            refreshLayout.finishRefresh()
        }

    }

    private fun initData() {
        updateUserInfo()
        val userInfo = DataManager.getInstance().queryUserInfo()
        if (userInfo != null) {
            viewModel.getUserAchievementById(userInfo.token, object : ResultJSONObjectObserver(lifecycle) {
                override fun onSuccess(result: JSONObject?) {
                    val userAccountBean = JSON.parseObject(result?.toString(), UserAccountBean::class.java)
                    tv_coin.setText("" + userAccountBean.coin)
                    tv_coupon_num.setText("" + userAccountBean.couponNum)
                }

                override fun onFail(msg: String?,code :Int) {
                }
            })

            viewModel.getOrderStatusCount(object : ResultJSONObjectObserver(lifecycle) {
                override fun onSuccess(result: JSONObject?) {
                    badge1.setBadgeNumber(result!!.optInt("unPay"))
                    badge2.setBadgeNumber(result!!.optInt("unSend"))
                    badge3.setBadgeNumber(result!!.optInt("unReceived"))

                }

                override fun onFail(msg: String?,code :Int) {
                    showToast(msg!!)
                }
            })

        } else {
            tv_name.setText("未登录")
            tv_phone.setText("点击头像登录")
            badge1.badgeNumber = 0
            badge2.badgeNumber = 0
            badge3.badgeNumber = 0
            tv_coin.text = "-"
            tv_coupon_num.text = "-"
            recycler_view.removeHeaderView(view2)
            list.clear()
            goodsListAdapter.setList(list)
            refreshLayout.finishRefresh()
        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initData()
        }
    }


    private lateinit var viewModel: UserInfoViewModel



    /**
     *
     */
    private fun updateUserInfo() {
        val userInfo = DataManager.getInstance().queryUserInfo()
        if (userInfo != null) {
            ImageLoader.setImageResource(context, R.mipmap.ic_default_logo, GlideCircleTransform(), false, iv_head)

            var phone = userInfo.phone
            phone = phone?.substring(0, 3) + "****" + phone?.substring(7, 11)
            tv_phone.setText(phone)
            val nickname = userInfo.nickname
            if (TextUtils.isEmpty(nickname)) {
                tv_name.setText("未设置昵称")
            } else {
                tv_name.setText(nickname)
            }
        } else {
            tv_name.setText("未登录")
            tv_phone.setText("点击头像登录")
        }
        if (userInfo != null) {
            showLoading()
            var data = JSONObject()
            data.put("userId", userInfo.id)
            viewModel.getUserInfo(userInfo.token, data, object : ResultJSONObjectObserver(lifecycle) {
                override fun onSuccess(result: JSONObject?) {
                    if (result != null) {
                        val userHeadPic = result.optString("userHeadPic")
                        ImageLoader.setImageResource(context, userHeadPic, R.mipmap.ic_head_placeholder, GlideCircleTransform(), false, iv_head)
                        val nickname = result.optString("nickname")
                        if (!TextUtils.isEmpty(nickname)) {
                            tv_name.setText(nickname)
                        } else {
                            tv_name.setText("未设置昵称")
                        }
                        var phone = userInfo.phone
                        phone = phone?.substring(0, 3) + "****" + phone?.substring(7, 11)
                        tv_phone.setText(phone)
                        val userLevel = result.optString("userLevel")
                        val isNewUser = result.optBoolean("isNewUser")

                        val queryUserInfo = DataManager.getInstance().queryUserInfo()

                        when (userLevel) {
                            "VIP " -> {
                                queryUserInfo.userLevel = 1;
                                tags = "VIPS"
                                DataManager.getInstance().insertUserInfo(queryUserInfo)
                            }
                            "STORE_KEEPER" -> {
                                queryUserInfo.userLevel = 2;
                                DataManager.getInstance().insertUserInfo(queryUserInfo)
                                queryUserInfo.userLevel = 1;
                                tags = "STORE_MGRS"
                                ARouter.getInstance().build("/store/main").navigation()
                            }
                            "STORE_MANAGER" -> {
                                queryUserInfo.userLevel = 3;
                                DataManager.getInstance().insertUserInfo(queryUserInfo)
                                tags = "STORE_MGRS"
                                ARouter.getInstance().build("/store/main").navigation()
                            }
                            "TEMP_STORE_KEEPER" -> {
                                queryUserInfo.userLevel = 4;
                                DataManager.getInstance().insertUserInfo(queryUserInfo)
                                tags = "STORE_MGRS"
                                ARouter.getInstance().build("/store/main").navigation()

                            }
                            "TEMP_STORE_MANAGER" -> {
                                queryUserInfo.userLevel = 5;
                                DataManager.getInstance().insertUserInfo(queryUserInfo)
                                tags = "STORE_MGRS"
                                ARouter.getInstance().build("/store/main").navigation()
                            }
                        }
                        if (isNewUser) {
                            var tags = setOf<String>("NEW_CUSTOMERS",tags)
                            val tagAliasBean = JPushTagAliasOperatorHelper.TagAliasBean(JPushTagAliasOperatorHelper.ACTION_SET, "" + queryUserInfo.id, tags, false)
                            JPushTagAliasOperatorHelper.getInstance().handleAction(context, JPushTagAliasOperatorHelper.sequence, tagAliasBean)
                        } else {
                            var tags = setOf<String>("OLD_CUSTOMERS",tags)
                            val tagAliasBean = JPushTagAliasOperatorHelper.TagAliasBean(JPushTagAliasOperatorHelper.ACTION_SET, "" + queryUserInfo.id, tags, false)
                            JPushTagAliasOperatorHelper.getInstance().handleAction(context, JPushTagAliasOperatorHelper.sequence, tagAliasBean)
                        }
                    }

                    hideLoading()
                }

                override fun onFail(msg: String?,code :Int) {
                    hideLoading()
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        when (id) {
            R.id.iv_message -> {
            }
            R.id.iv_connnet_kf -> {
                ARouter.getInstance().build("/base/qiyukefu").navigation()
            }
            R.id.ll_user_info -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo != null) {
                    ARouter.getInstance().build("/vip/user_info").navigation()

                } else {
                    ARouter.getInstance().build("/base/login_phone").navigation()
                }
            }
            R.id.ll_invate -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/store/invate_store").withString("url", BuildConfig.H5BASEURL + "#/openShop?recommendCode" + userInfo.recommendCode + "&token=" + userInfo.token + "&userLevel=" + userInfo.userLevel).navigation()
                }

            }
            R.id.ll_jiaobi -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/coin/coin").navigation()
                }

            }
            R.id.ll_coupin -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/coupon/coupon").navigation()
                }
            }
            R.id.rl_order_center -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/order/manager").withString("url", BuildConfig.H5BASEURL + "#/?tabIndex=0&token=" + userInfo.token).navigation()
                }
            }
            R.id.ll_wait_pay -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/order/manager").withString("url", BuildConfig.H5BASEURL + "#/?tabIndex=1&token=" + userInfo.token).navigation()
                }

            }
            R.id.ll_wait_send -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/order/manager").withString("url", BuildConfig.H5BASEURL + "#/?tabIndex=2&token=" + userInfo.token).navigation()
                }

            }
            R.id.ll_deliver -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {
                    ARouter.getInstance().build("/order/manager").withString("url", BuildConfig.H5BASEURL + "#/?tabIndex=3&token=" + userInfo.token).navigation()
                }

            }
            R.id.ll_service -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo == null) {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                    return
                } else {

                    ARouter.getInstance().build("/order/service").withString("url", BuildConfig.H5BASEURL + "#/SalesManagement?token=" + userInfo.token).navigation()
                }

            }
            R.id.rl_accept_address -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo != null) {
                    ARouter.getInstance().build("/address/list").navigation()
                } else {
                    showToast("请先登录！")
                    ARouter.getInstance().build("/base/login_phone").navigation()
                }
            }
            R.id.rl_help -> {
            }
            R.id.re_setting -> {
                val userInfo = DataManager.getInstance().queryUserInfo()
                if (userInfo != null) {
                    ARouter.getInstance().build("/vip/setting").navigation()
                } else {
                    ARouter.getInstance().build("/base/login_phone").navigation()
                }

            }


        }

    }
}
