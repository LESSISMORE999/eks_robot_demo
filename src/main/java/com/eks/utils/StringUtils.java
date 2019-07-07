package com.eks.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {
    public static String convertStringByCharsetString(String contentString, String oldCharsetString, String newCharsetString) throws UnsupportedEncodingException {
        if (contentString == null || "".equals(contentString)){
            return contentString;
        }
        byte[] byteArray = contentString.getBytes(oldCharsetString);
        return new String(byteArray, newCharsetString);
    }
}