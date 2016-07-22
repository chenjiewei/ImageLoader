package com.example.cwx343067.imageloader.manager.utils;

import java.security.MessageDigest;

/**
 * Created by cWX343067 on 2016/7/21.
 * 这个类主要的功能是求出URL的MD5值
 */
public class HexToStrByMD5 {
    /**
     * URL转化成字符串,使用MD5实现
     * @param urlValue
     * @return
     */
    public static final synchronized String hashKeyForDiskName(String urlValue) {
        String urlMD5 = null;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(urlValue.getBytes());
            urlMD5 = bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出现异常使用对象的哈希值
            urlMD5 = String.valueOf(urlValue.hashCode());
        } finally {

        }
        return urlMD5;
    }

    /**
     * 字节数组转化成int，整形在转換16进制字符串
     * @param bytes
     * @return
     */
    private static final String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
