package com.gialen.baselib.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

//import com.bumptech.glide.Glide;
import com.gialen.baselib.R;
//import com.gialen.baselib.application.BaseApplication;


public class LoadingDialog extends Dialog {

    private Context context;
    private  View contentView;
    private ImageView iv_progress_dialog;
    private volatile boolean isShowing;

    public LoadingDialog(@NonNull Context context) {
        this(context, 0);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        contentView = LayoutInflater.from(context).inflate(R.layout.view_loading_dialog, null);
        contentView.setLayoutParams(params);
        iv_progress_dialog = (ImageView) contentView.findViewById(R.id.iv_progress_dialog);


        //Glide.with(BaseApplication.instance.getApplicationContext()).asGif().load(R.drawable.loading).into(iv_progress_dialog);
        this.setContentView(contentView);
        this.setCanceledOnTouchOutside(false);
    }





    public  void hide() {
        this.cancel();
    }



}