package com.eks.utils;

import com.eks.enumeration.FileTypeEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void closeStream(InputStream inputStream){
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeStream(OutputStream outputStream){
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String fileToHexString(String filePathString) throws IOException {
        InputStream fileInputStream = null;
        try {
            File file = new File(filePathString);
            fileInputStream = new FileInputStream(file);
            int value = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((value = fileInputStream.read()) != -1) {
                stringBuilder.append(String.format("%02X", value));
            }
            return stringBuilder.toString();
        }finally {
            closeStream(fileInputStream);
        }
    }
    public static String getFileRealTypeString(String filePathString) throws IOException {
        String fileHexString = fileToHexString(filePathString);
        if (fileHexString.startsWith(FileTypeEnum.jpg.getStartHexString())){
            return FileTypeEnum.jpg.name();
        }
        if (fileHexString.startsWith(FileTypeEnum.png.getStartHexString())){
            return FileTypeEnum.png.name();
        }
        return null;
    }
    public static String integerToHexString(Integer integer){
        return  Integer.toHexString(integer);
    }
    public static Integer binaryStringToInteger(String binaryString){
        return Integer.valueOf(binaryString, 2);
    }
    public static String binaryStringToHexString(String binaryString){
        Integer normalInteger = Integer.valueOf(binaryString, 2);
        return Integer.toHexString(normalInteger);
    }
    public static String integerToBinaryString(Integer integer){
        return  Integer.toBinaryString(integer);
    }
    public static Integer hexStringToInteger(String hexString) {
        return Integer.valueOf(hexString, 16);
    }
    public static String hexStringToBinaryString(String hexString) {
        Integer normalInteger = Integer.valueOf(hexString, 16);
        return Integer.toBinaryString(normalInteger);
    }
    public static void copyFile(String inputFilePathString, String outputFilePathString) throws IOException {
        decryptFile(inputFilePathString,outputFilePathString,null);
    }
    public static void decryptFile(String inputFilePathString, String outputFilePathString, Integer xorInteger) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(inputFilePathString);
            createFile(outputFilePathString);
            fileOutputStream = new FileOutputStream(outputFilePathString);
            int readInt = 0;
            while ((readInt = fileInputStream.read()) != -1) {
                if (xorInteger != null){
                    fileOutputStream.write(readInt ^ xorInteger);
                }else{
                    fileOutputStream.write(readInt);
                }
            }
        } finally {
            closeStream(fileInputStream);
            closeStream(fileOutputStream);
        }
    }
    public static Boolean createFile(String filePathString) throws IOException {
        File file = new File(filePathString);
        if (file.exists()) {
            return true;
        }
        if (!file.getParentFile().exists()) {
            boolean mkdirsBoolean = file.getParentFile().mkdirs();
            if (!mkdirsBoolean){
                throw new RuntimeException("Failed to create folder.");
            }
        }
        boolean newFileBoolean = file.createNewFile();
        if (!newFileBoolean){
            throw new RuntimeException("Failed to create file.");
        }
        return true;
    }
    public static List<String> getAllFilePathBaseDir(String dirPathString){
        List<String> filePathStringList = new ArrayList<>();
        return getAllFilePathBaseDir(dirPathString,filePathStringList);
    }
    private static List<String> getAllFilePathBaseDir(String dirFilPathString,List<String> filePathStringList) {
        File file = new File(dirFilPathString);
        if (!file.exists()){
            throw new RuntimeException("File does not exist.");
        }
        if (file.isFile()) {
            filePathStringList.add(file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            String[] fileNameStringArray = file.list();
            if (fileNameStringArray != null && fileNameStringArray.length > 0) {
                for (String fileNameString : fileNameStringArray) {
                    String newFilePathString = dirFilPathString + File.separator + fileNameString;
                    getAllFilePathBaseDir(newFilePathString, filePathStringList);
                }
            }
        }
        return filePathStringList;
    }
    public static boolean deleteFile(String filePathString) {
        File file = new File(filePathString);
        if (!file.exists()){
            throw new RuntimeException("File does not exist.");
        }
        if (!file.isFile()){
            throw new RuntimeException("This is not a file.");
        }
        return file.delete();
    }
}
