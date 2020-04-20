package com.gialen.baselib.net.client;


import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.gialen.baselib.BuildConfig;
import com.gialen.baselib.R;
import com.gialen.baselib.data_manager.SharedPreferencesManager;
import com.gialen.baselib.net.HTTPSCerUtils;
import com.hengte.retrofit.net.converterfactory.JSONObjectConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * create by tdd
 * date 2016/8/5 0005
 * des 对网络请求进行初始化
 */
public class RetrofitClient {
    public static Retrofit mRetrofit;


    //初始化一般请求客户端
    public static Retrofit init(Context context,String baseUrl) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

          final String sessionId=  SharedPreferencesManager.getInstance().getString(SharedPreferencesManager.SPCommons.SESSIONID,"");
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("platfrom", "1")
                            .addHeader("terminal", "2")
                            .addHeader("sessionId", sessionId)
                            .addHeader("appVersion", BuildConfig.VERSION_NAME)
                            .addHeader("mobileModel", Build.BRAND+" "+Build.MODEL )
                            .addHeader("IMEI",  Build.SERIAL)
                            .build();
                    return chain.proceed(request);
                }

            });
            if (BuildConfig.DEBUG) {

                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("http", message);
                    }
                });
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);

            }
            HTTPSCerUtils.setCertificate(context, builder, R.raw.gialen);
//            HTTPSCerUtils.setTrustAllCertificate(builder);
//            builder.sslSocketFactory(createSSLSocketFactory());

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.readTimeout(5, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();

            //组装retrofit
            mRetrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JSONObjectConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return mRetrofit;
    }




}
