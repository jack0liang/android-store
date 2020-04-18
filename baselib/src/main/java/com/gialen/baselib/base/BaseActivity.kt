package com.gialen.baselib.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gialen.baselib.R
import com.gialen.baselib.util.SystemStyleStatusUtils
import com.gialen.baselib.widget.loading.LoadingDialog
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val DEFAULT_STATUS_BAR_COLOR = Color.GRAY

    protected val toast: Toast by lazy {
        Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    protected abstract fun getLayoutId(): Int


    protected//获取status_bar_height资源的ID
    //根据资源ID获取响应的尺寸值
    val statusBarHeight: Int
        get() {
            var height = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                height = resources.getDimensionPixelSize(resourceId)
            }
            return height
        }


    override fun finish() {
        super.finish()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
//            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity)
//        }
    }


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setStatusBarColor(DEFAULT_STATUS_BAR_COLOR)
        initBase()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mContext = this
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        initView(savedInstanceState)

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                lazzyLoad()
                window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    var loadingDialog: LoadingDialog? = null

    protected open fun showLoading() {
        if (!this.isDestroyed) {
            loadingDialog?.show()
        }
    }

    protected open fun hideLoading() {
        if (!this.isDestroyed&&loadingDialog != null) {
            loadingDialog?.hide()
        }
    }


    protected open fun initBase() {}


    protected open fun lazzyLoad() {}


    protected open fun initView(savedInstanceState: Bundle?) {}


    /**
     * 隐藏键盘
     */
    open fun hideKeyBoard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * 显示键盘
     */
    open fun showKeyBoard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(v, 0)
    }


    fun showToast(id: Int) {
        showToast(getString(id))
    }

    fun showToast(msg: String) {
        toast.setText(msg)
        toast.show();
    }

    fun cancelToast() {
        toast.cancel();
    }


    /**
     * 设置沉浸状态栏
     */
    protected fun setImmersionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setAndroidNativeLightStatusBar(dark: Boolean) {
        val decor = window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }


    // 在setContentView之前执行
    private fun setStatusBar(color: Int) {
        /*
         为统一标题栏与状态栏的颜色，我们需要更改状态栏的颜色，而状态栏文字颜色是在android 6.0之后才可以进行更改
         所以统一在6.0之后进行文字状态栏的更改
        */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                SystemStyleStatusUtils.transparencyBar(this)
            } else {
                SystemStyleStatusUtils.setStatusBarColor(this, setStatusBarColor(color))
            }
            SystemStyleStatusUtils.setLightStatusBar(this, false)
        } else {
            if (isUseFullScreenMode()) {
                SystemStyleStatusUtils.transparencyBar(this)
            } else {
                SystemStyleStatusUtils.setStatusBarColor(this, setStatusBarColor(color))
            }
            SystemStyleStatusUtils.setLightStatusBar(this, false)
        }

    }

    // 是否设置成透明状态栏，即就是全屏模式
    protected fun isUseFullScreenMode(): Boolean {
        return true
    }

    protected fun setStatusBarColor(color: Int): Int {
        return color
    }


}
