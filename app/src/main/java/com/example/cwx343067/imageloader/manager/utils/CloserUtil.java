package com.example.cwx343067.imageloader.manager.utils;

import java.io.Closeable;

/**
 * Created by cWX343067 on 2016/7/18.
 */
public class CloserUtil {
    /**
     * 关闭可关闭对象
     * @param closeable
     */
    public static final void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }

        }
    }
}
