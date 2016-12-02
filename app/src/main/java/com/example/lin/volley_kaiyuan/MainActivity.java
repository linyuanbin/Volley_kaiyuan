package com.example.lin.volley_kaiyuan;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView,textView2;
    private ImageView imageView;
    private NetworkImageView networkImageView;
    private String url="http://apis.baidu.com/heweather/weather/free";
    private String arg="city=beijing";
    private String ur=url+"?"+arg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView) findViewById(R.id.text);
        textView2= (TextView) findViewById(R.id.text2);
        imageView= (ImageView) findViewById(R.id.imageView);
        networkImageView= (NetworkImageView) findViewById(R.id.networkImage);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        //StringRequest stringRequest=new StringRequest(ur, new Response.Listener<String>() {  //不使用getParams传入参数时这样就行
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {//Request.Method.POST请求方式
            @Override
            public void onResponse(String s) {
                textView.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView.setText(volleyError.getMessage()+"error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("city ","jilin");  //对应的api需要什么这里就写什么
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("apikey ","af02a3e0e93ef6cbb85eada01a1c40ae");
                return map;
            }
        };

        //JsonRequest的使用
        /*使用JsonObjectRequest或继承自JsonObjectRequest类的对象提交post请求时，
        如果有参数需要提交时必须一JsonObject的json串方式提交，否则通过getParams（）方法的方式提交不管用
        */
        JsonObjectRequest JsonObjectRequest=new JsonObjectRequest("http://apis.baidu.com/heweather/weather/free?city=beijing", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                textView2.setText(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView2.setText(volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("apikey ","af02a3e0e93ef6cbb85eada01a1c40ae");
                return map;
            }
        };

        //第一个URL图片的网址   第二个Listener  第三第四为最大宽和高，第五为图片质量Config.ARGB_8888四个字节，ARGB_565 ARGB_4444两个字节  第六个ErrorListener
         ImageRequest imageRequest=new ImageRequest("https://p.ssl.qhimg.com/t01df31145427f5ea2f.jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(imageRequest);
       // requestQueue.add(JsonObjectRequest);
       // requestQueue.add(stringRequest);



        //使用networkImageView
        //首先定义出ImageLoader
        ImageLoader imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);//加载失败显示图片
        networkImageView.setImageUrl("https://p.ssl.qhimg.com/t01df31145427f5ea2f.jpg",imageLoader);
    }
}
