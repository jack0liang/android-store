package com.gialen.baselib.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gialen.baselib.BuildConfig;
import com.gialen.baselib.data_manager.DataManager;
import com.gialen.baselib.net.client.RetrofitClient;

public abstract class BaseApplication extends Application {
    protected static Context context;

    public void onCreate(){
        super.onCreate();
        BaseApplication.context = getApplicationContext();
        DataManager.init(this);//数据库统一操作管理类初始化
        ARouter.init(this);
        RetrofitClient.init(this, BuildConfig.BASEURL);
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }
}
