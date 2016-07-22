package com.example.cwx343067.imageloader.manager.image.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.cwx343067.imageloader.manager.utils.CloserUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cWX343067 on 2016/7/19.
 */
public class ImageDiskCacheUitl {
    /**
     * 获取缓存路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static final File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 根据URL，下载图片数据
     * @param urlPath 图片URL地址
     * @param outputStream 装载图片内容的数据流
     * @return
     */
    public static final synchronized boolean downLoadUrlToStream(String urlPath, OutputStream outputStream) {
        HttpURLConnection httpURLConnection = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            final URL url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 默认缓冲区大小是8M
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int bytesize;
            while ((bytesize = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(bytesize);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            CloserUtil.close(bufferedOutputStream);
            CloserUtil.close(bufferedInputStream);
        }
        return false;
    }
}
