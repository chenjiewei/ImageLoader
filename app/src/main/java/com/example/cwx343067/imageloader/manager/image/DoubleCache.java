package com.example.cwx343067.imageloader.manager.image;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by cWX343067 on 2016/7/18.
 */
public class DoubleCache implements ImageCacheInterface {
    private MemoryImageCache mMemoryImageCache = null;
    private ImageDiskCache mImageDiskCache = null;
    private Context mContext = null;

    public DoubleCache(Context context) {
        mContext = context;
        mImageDiskCache = new ImageDiskCache(mContext);
        mMemoryImageCache = new MemoryImageCache();
    }

    /**
     * 从内存或者是从sdcard里面获取
     *
     * @param url
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        bitmap = mMemoryImageCache.getBitmap(url) == null ? mImageDiskCache.getBitmap(url) : mMemoryImageCache.getBitmap(url);
        return bitmap;
    }

    /**
     * 将数据缓存到sdcard和内存中
     *
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mMemoryImageCache.putBitmap(url, bitmap);
        mImageDiskCache.putBitmap(url, bitmap);
    }

}
