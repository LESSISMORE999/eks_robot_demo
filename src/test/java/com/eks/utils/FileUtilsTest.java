package com.eks.utils;

import org.junit.Test;

import java.io.IOException;

public class FileUtilsTest {
    String filePathString = "D:\\20190112_1105_GitHub_NameSpace\\eks_robot_demo\\src\\main\\resources\\wechat\\3f9d3219e813471212e91b1e429c8f8f.dat";
    String filePathString2 = "D:\\20190112_1105_GitHub_NameSpace\\eks_robot_demo\\src\\main\\resources\\wechat\\3f9d3219e813471212e91b1e429c8f8f.jpg";
    @Test
    public void test1() throws IOException {
        String fileHexString = FileUtils.fileToHexString(filePathString2);
        System.out.println(fileHexString);
        System.out.println(FileUtils.getFileRealTypeString(filePathString2));
    }
    @Test
    public void test2() {
        String binaryString = FileUtils.hexStringToBinaryString("89504E");
        System.out.println(binaryString);
        String hexString = FileUtils.binaryStringToHexString(binaryString);
        System.out.println(hexString);
    }
    @Test
    public void test3() {
        Integer integer1 = FileUtils.hexStringToInteger("89");
        Integer integer2 = FileUtils.hexStringToInteger("89");
        int i = integer1 ^ integer2;
        System.out.println(FileUtils.integerToHexString(i));
    }
    @Test
    public void test4() throws IOException {
        String fileHexString = FileUtils.fileToHexString(filePathString);
        String substring = fileHexString.substring(0, 2);
        System.out.println(substring);
        Integer integer1 = FileUtils.hexStringToInteger(substring);
        Integer integer2 = FileUtils.hexStringToInteger("FF");
        int integer = integer1 ^ integer2;
        System.out.println(integer);
        System.out.println(FileUtils.integerToHexString(integer));
    }
}
