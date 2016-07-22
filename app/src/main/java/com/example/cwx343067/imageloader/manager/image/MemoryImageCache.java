package com.example.cwx343067.imageloader.manager.image;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cWX343067 on 2016/7/18.
 */
public class MemoryImageCache implements ImageCacheInterface {
    // 图片缓存
    private LruCache<String, Bitmap> mImageCache;
    // 构造器
    public MemoryImageCache() {
        initImageCache();
    }

    //初始话图片缓存数据
    public void initImageCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        // 取四分之一作为缓存
        final int cacheSize = maxMemory / 4;
        // 初始化图片换成对象
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mImageCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

        mImageCache.put(url, bitmap);
    }
}
