package com.example.cwx343067.imageloader.manager.image;

import android.graphics.Bitmap;

/**
 * Created by cWX343067 on 2016/7/19.
 */
public interface ImageCacheInterface {
    public Bitmap getBitmap(String url);

    public void putBitmap(String url, Bitmap bitmap);
}
