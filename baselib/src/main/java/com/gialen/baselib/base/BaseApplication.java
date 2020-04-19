package com.gialen.baselib.base;

import android.app.Application;
import android.content.Context;

public abstract class BaseApplication extends Application {
    protected static Context context;

    public void onCreate(){
        super.onCreate();
        BaseApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }
}
