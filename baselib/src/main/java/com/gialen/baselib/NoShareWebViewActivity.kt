package com.gialen.baselib

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.gialen.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_no_share_web_view.*
import kotlinx.android.synthetic.main.activity_no_share_web_view.image_bar_right
import kotlinx.android.synthetic.main.activity_no_share_web_view.li_back
import kotlinx.android.synthetic.main.activity_no_share_web_view.title_bar_title
import kotlinx.android.synthetic.main.activity_no_share_web_view.webView

@Route(path = "/base/noshare_webview")
class NoShareWebViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_no_share_web_view;
    }
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val bundle = intent.extras
        var url = bundle?.getString("url")
        val title = bundle?.getString("title")
        image_bar_right.visibility= View.INVISIBLE
        title_bar_title.setText(title)
        li_back.setOnClickListener { finish() }
        initWebView()
        webView.loadUrl(url)
    }

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        val webSettings = webView.settings
        webSettings.domStorageEnabled = true//主要是这句
        webSettings.javaScriptEnabled = true//启用js
        webSettings.blockNetworkImage = false//解决图片不显示
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true

        WebView.setWebContentsDebuggingEnabled(true);
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.setWebChromeClient(
                WebChromeClient())
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

    }





}