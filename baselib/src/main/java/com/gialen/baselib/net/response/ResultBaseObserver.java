package com.gialen.baselib.net.response;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gialen.baselib.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ResultBaseObserver<T> implements Observer<JSONObject> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(JSONObject jsonObject) {
        int code = JsonUtil.getInt(jsonObject, "code");
        String msg = JsonUtil.getString(jsonObject, "message");
        Boolean flag = JsonUtil.getBoolean(jsonObject, "success");
        if(code==200){
            responData(jsonObject);
        }else if(code==3001||code==3401){
            onFail(msg,code);
            ARouter.getInstance().build("/base/login_phone").navigation();
        } else{
            onFail(msg,code);
        }
    }

    protected abstract void onFail(String msg, int code);

    private void responData(JSONObject jsonObject) {
        Object data = JsonUtil.getObject(jsonObject,"data");
        if(data==null){
            onSuccess(null);
            return ;
        }
        if(data instanceof JSONArray){
            onSuccess((T)data);
        }else if(data instanceof JSONObject){
            onSuccess((T)data);
        }
    }

    protected abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
