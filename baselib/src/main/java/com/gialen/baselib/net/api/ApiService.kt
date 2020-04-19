package com.gialen.baselib.net.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import org.json.JSONObject

import java.util.HashMap

import io.reactivex.Observable

interface ApiService {

    /**
     * post请求
     *
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST()
    fun postRequest(
            @Url url: String,
            @Header("token") token: String,
            @Header("sign") sign: String,
            @Header("reqDate") reqDate: String,
            @Body requestBody: RequestBody): Observable<JSONObject>

    /**
     * post请求
     *
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST()
    fun postLogin(@Url url: String,
                  @Header("sign") sign: String,
                  @Header("reqDate") reqDate: String,
                  @Body requestBody: RequestBody): Observable<JSONObject>


    /**
     * post请求
     *
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST()
    fun postBuriedPoint(@Url  url: String,
                        @Header("reqDate") reqDate: String,
                        @Body requestBody: RequestBody): Observable<JSONObject>

    /**
     * post请求
     *
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST()
    fun postRequest(
            @Url url: String,
            @Header("sign") sign: String,
            @Header("reqDate") reqDate: String,
            @Body requestBody: RequestBody): Observable<JSONObject>

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Observable<ResponseBody>


    /**
     * 上传文件
     *
     * @param url
     * @param token
     * @param body
     * @return
     */

    @POST("{url}")
    fun uploadFile(
            @Url url: String,
            @Header("token") token: String,
            @Body body: RequestBody): Observable<JSONObject>

    /*POST 请求 上传单个文件*/
    @Multipart
    @POST()
    abstract fun upLoadImage(
            @Url url: String,
            @Header("token") token: String,
            @Part file: MultipartBody.Part
    ): Observable<JSONObject>


    /*POST 请求 上传文件*/
    @Multipart
    @POST
    abstract fun uploadFiles(@Url url: String,
                             @HeaderMap headers: Map<String, String>,
                             @Query("fileType") param: String,
                             @Part parts: List<MultipartBody.Part>): Observable<JSONObject>


}

