package com.eks.utils;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class WeChatUtils {
    @SuppressWarnings("ALL")
    public static Map<Integer,Object> getCfgFile(String filePathString){
        Map<Integer,Object> map = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            fileInputStream = new FileInputStream(filePathString);
            objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map<Integer, Object>) objectInputStream.readObject();
        }catch (Exception e){
            FileUtils.closeStream(objectInputStream);
            FileUtils.closeStream(fileInputStream);
        }
        return map;
    }
    public static String getValueString(String filePathString,Integer keyInteger){
        Map<Integer, Object> map = getCfgFile(filePathString);
        return String.valueOf(map.get(keyInteger));
    }
    public static String getImeiString(String filePathString){
        return getValueString(filePathString,258);
    }
    public static String getUniString(String filePathString){
        return getValueString(filePathString,1);
    }
    public static String encodeMd5(String uncodedString){
        MessageDigest messageDigest = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(uncodedString.getBytes());
            byte[] byteArray = messageDigest.digest();
            int index;
            for(byte b : byteArray) {
                index = b;
                if(index < 0){
                    index += 256;
                }
                if(index < 16){
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(index));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public static String getWeChatKeyString(String imeiString,String uniString){
        String encodedMd5String = encodeMd5(imeiString + uniString);
        return encodedMd5String.substring(0,7);
    }
}
