package com.example.cwx343067.imageloader.manager.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by cWX343067 on 2016/7/22.
 */
public enum ImageLoaderSingle {

    IMAGELOADER;

    private ImageLoader mImageLoader = null;

    /**
     * 获取ImageLoader的方法，主要是为了隐藏ImageLoader的方法，所以才在这里设置为私有的方法
     * @param context 上下文
     * @return
     */
    private ImageLoader getImageLoaderInstances(Context context) {
        if(mImageLoader != null){
            return mImageLoader;
        }
        if (context != null) {
            mImageLoader = new ImageLoader(context);
        }
        return mImageLoader;
    }

    /**
     * 客户端调用的方法，这个枚举只有一个子类对象
     * 这个方法是显示图片的
     * @param context 上下问
     * @param url 图片的url
     * @param imageView 需要显示图片的ImageView
     */
    public void displayImage(Context context , String url, ImageView imageView){
        if(mImageLoader == null){
            mImageLoader = getImageLoaderInstances(context);
        }
        mImageLoader.displayImage(url,imageView);
    }

    /**
     * 设置自定义的缓存模式，这个方法是注入依赖
     * @param context 上下文
     * @param imageCacheInterface 缓存接口
     */
    public void setImageCacheInterface(Context context,ImageCacheInterface imageCacheInterface){
        if(mImageLoader == null){
            mImageLoader = getImageLoaderInstances(context);
        }
        mImageLoader.setImageCacheInterface(imageCacheInterface);
    }


}
