package com.example.cwx343067.imageloader.manager.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cwx343067.imageloader.manager.image.util.ImageDiskCacheUitl;
import com.example.cwx343067.imageloader.manager.utils.CloserUtil;
import com.example.cwx343067.imageloader.manager.utils.HexToStrByMD5;
import com.example.cwx343067.imageloader.manager.utils.ObtainAppVersion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by cWX343067 on 2016/7/18.
 */
public class ImageDiskCache implements ImageCacheInterface {
    private DiskLruCache mDiskLruCache = null;
    private Context mContext = null;

    public ImageDiskCache(Context context) {
        mContext = context;
        initDiskLruCache();
    }

    private void initDiskLruCache() {
        try {
            File file = ImageDiskCacheUitl.getDiskCacheDir(mContext, ImageLoaderConfig.IMAGE_CACHE_DIRS_NAME);
            if(!file.exists()){
                file.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(file, ObtainAppVersion.obtainVersion(mContext),1,ImageLoaderConfig.CACHE_MEMORY_SIZE);
        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * 从缓存中获取图片
     *
     * @param url :key值
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        String key = HexToStrByMD5.hashKeyForDiskName(url);
        try{
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
        return bitmap;
    }

    /**
     * 将图片缓存到sdcard中
     *
     * @param url    key值
     * @param bitmap 对象值
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String key = HexToStrByMD5.hashKeyForDiskName(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null && bitmap != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                // 压缩不变大小
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}
