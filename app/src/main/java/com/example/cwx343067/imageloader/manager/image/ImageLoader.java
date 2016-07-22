package com.example.cwx343067.imageloader.manager.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cWX343067 on 2016/7/18.
 */
public class ImageLoader {
    // 图片缓存
    private ImageCacheInterface mImageCache = null;
    // 线程池
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // 上下文
    private Context mContext = null;

    public ImageLoader(Context context) {
        mContext = context;
        mImageCache = new MemoryImageCache();
    }

    /**
     * 注入缓存实现
     *
     * @param imageCacheInterface
     */
    public void setImageCacheInterface(ImageCacheInterface imageCacheInterface) {
        mImageCache = imageCacheInterface;
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     */
    public void displayImage(String url, ImageView imageView) {
        Bitmap bitmap = mImageCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        // 如果缓存没有，由网络下载
        submitLoadRequest(url, imageView);

    }

    /**
     * 提交任务到线程池中去获取数据
     *
     * @param imageUrl
     * @param imageView
     */
    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.putBitmap(imageUrl, bitmap);
            }
        });
    }

    /**
     * 根据URL，下载图片
     *
     * @param imageUrl
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return bitmap;
    }
}
