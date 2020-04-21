package com.gialen.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gialen.baselib.widget.loading.LoadingDialog;

public abstract class DataBingBaseActivity extends AppCompatActivity {

    LoadingDialog loadingDialog;
    protected Toast toast;
    protected void showLoading(){
        if (loadingDialog!=null) {
            loadingDialog.show();
        }
    }
    protected void hideLoading(){
        if (loadingDialog!=null){
            loadingDialog.hide();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //toast =  Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    protected abstract int getLayoutId();



    protected void showToast(String msg) {
        if(toast==null)
            toast =  Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 显示键盘
     */
    protected void showKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }
    /**
     * 隐藏键盘
     */
    protected void hideKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
