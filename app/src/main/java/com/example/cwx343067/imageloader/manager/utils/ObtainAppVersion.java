package com.example.cwx343067.imageloader.manager.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by cWX343067 on 2016/7/19.
 */
public class ObtainAppVersion {
    public static final int obtainVersion(Context context) {
        PackageInfo packInfo;
        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return 1;
    }
}
