package com.example.lin.volley_kaiyuan;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by lin on 2016/12/5.
 */

//自定义ImagCache   Lru算法是android提供的

public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> bitmapLruCache;
    public BitmapCache(){
        int maxsize=10*1024*1024;//定义大小为10兆
        bitmapLruCache=new LruCache<String,Bitmap>(maxsize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s) {
        return bitmapLruCache.get(s);
    }
    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        bitmapLruCache.put(s,bitmap);
    }
}
