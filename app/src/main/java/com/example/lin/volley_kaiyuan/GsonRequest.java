package com.example.lin.volley_kaiyuan;

import android.app.DownloadManager;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;

/**
 * Created by lin on 2016/12/2.
 */

/*
自定义request
Gson jar包需要导入，这里我没有导入所有报错
下载地址：http://download.csdn.net/detail/zeng_zhi_1991/9452466
 */
public class GsonRequest<T> extends Request<T>{

    private Response.Listener<T> listener;
    private Gson gson;
    private Class<T> tClass;

    public GsonRequest(String url, Class<T> tClass,Response.Listener listener, Response.ErrorListener ErrorListener){
        super(Method.GET,url,tClass,listener,ErrorListener);
    }

    public GsonRequest(int method, String url, Class<T> tClass,Response.Listener listener, Response.ErrorListener ErrorListener){
        super(method,url,ErrorListener);
        this.gson=new Gson();
        this.tClass=tClass;
        this.listener=listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
                    String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers))
                    return Response.success(gson.fromJson(jsonString,tClass),HttpHeaderParser.parseCacheHeaders(networkResponse));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }
        return null;
    }

    @Override
    protected void deliverResponse(T t) {

    }
}
