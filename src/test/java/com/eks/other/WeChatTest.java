package com.eks.other;

import com.eks.enumeration.FileTypeEnum;
import com.eks.utils.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class WeChatTest {
    @Test
    public void test1() {
        RobotUtils.clickMouse(500,500, 2277, 284);
        RobotUtils.pressKey(500,false,KeyEvent.VK_E,KeyEvent.VK_K,KeyEvent.VK_S);
        RobotUtils.pressKey(500,false,KeyEvent.VK_ENTER);
    }
    @Test
    public void test2() {
        Point mousePoint = RobotUtils.getMousePoint();
        System.out.println(mousePoint.getX());
        System.out.println(mousePoint.getY());
    }
    @Test
    public void test3() throws IOException {
        JsonArray responseJsonArray = OkHttpUtils.sendRequest("http://api.laifudao.com/open/xiaohua.json").getAsJsonArray();
        System.out.println("responseJsonArray:" + responseJsonArray);
        JsonElement jsonElement = GsonUtils.generateJsonElementWithNeededField(responseJsonArray, "title", "content");
        System.out.println("jsonElement:" + jsonElement);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        StringBuilder stringBuilder = new StringBuilder("笑话三则：");
        stringBuilder.append("\r\n");
        for(int i = 0,length = jsonArray.size();i < length;i++){
            if (i == 3){
                break;
            }
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            stringBuilder.append(i + 1);
            stringBuilder.append(".");
            stringBuilder.append(jsonObject.get("title").getAsString());
            stringBuilder.append("\r\n");
            String contentString = jsonObject.get("content").getAsString();
            contentString = contentString.replaceAll("<br/><br/>", "\r\n");
            stringBuilder.append(contentString);
            stringBuilder.append("\r\n");
        }
        String threeJokeString = stringBuilder.toString();
        System.out.println(threeJokeString);
        ClipboardUtils.setClipboardText(threeJokeString);
        RobotUtils.clickMouse(500,500, 2277, 284);
        RobotUtils.pressKey(500, false,KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
    }
    @Test
    public void test4() throws IOException {
        JsonObject responseJsonObject = OkHttpUtils.sendRequest("https://www.apiopen.top/journalismApi").getAsJsonObject();
        JsonObject dataJsonObject = responseJsonObject.get("data").getAsJsonObject();
        JsonArray moneyJsonArray = dataJsonObject.get("money").getAsJsonArray();
        JsonArray simpleMoneyJsonArray = GsonUtils.generateJsonElementWithNeededField(moneyJsonArray, "title","link").getAsJsonArray();
        System.out.println(simpleMoneyJsonArray);
        StringBuilder stringBuilder = new StringBuilder("每日财经新闻：");
        stringBuilder.append("\r\n");
        int i = 1;
        for(JsonElement jsonElement : simpleMoneyJsonArray){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            stringBuilder.append(i);
            stringBuilder.append(".");
            stringBuilder.append(jsonObject.get("title").getAsString());
            stringBuilder.append("\r\n");
            String contentString = jsonObject.get("link").getAsString();
            stringBuilder.append(contentString);
            stringBuilder.append("\r\n");
            i++;
        }
        String threeJokeString = stringBuilder.toString();
        System.out.println(threeJokeString);
        ClipboardUtils.setClipboardText(threeJokeString);
        RobotUtils.clickMouse(500,500, 2277, 284);
        RobotUtils.pressKey(500, false,KeyEvent.VK_CONTROL,KeyEvent.VK_V);
        RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
    }
    @Test
    public void test5() throws IOException {
        String dirPathString = "D:\\TEMP\\input";
        List<String> filePathStringList = FileUtils.getAllFilePathBaseDir(dirPathString);
        for(String filePathString : filePathStringList){
            if (filePathString.endsWith(".dat")){
                String filePathWithoutDirPathString = filePathString.substring(dirPathString.length(), filePathString.length());

                String fileHexString = FileUtils.fileToHexString(filePathString);
                String firstHexString = fileHexString.substring(0, 2);
                Integer firstHexStringInteger = FileUtils.hexStringToInteger(firstHexString);

                FileTypeEnum[] fileTypeEnumArray = FileTypeEnum.values();
                Boolean successBoolean = false;
                for(FileTypeEnum fileTypeEnum : fileTypeEnumArray){
                    String fileTypeEnumStartHexString = fileTypeEnum.getStartHexString();
                    String fileTypeEnumStartHexStringSubstring = fileTypeEnumStartHexString.substring(0, 2);
                    Integer integer2 = FileUtils.hexStringToInteger(fileTypeEnumStartHexStringSubstring);
                    int xorInteger = firstHexStringInteger ^ integer2;

                    String secondHexString = fileHexString.substring(2, 4);
                    Integer secondHexStringInteger = FileUtils.hexStringToInteger(secondHexString);
                    int secondXorInt = secondHexStringInteger ^xorInteger;
                    String secondXorString = FileUtils.integerToHexString(secondXorInt);

                    String secondFileTypeEnumString = fileTypeEnumStartHexString.substring(2, 4).toLowerCase();
                    if (!secondFileTypeEnumString.equals(secondXorString.toLowerCase())){
                        continue;
                    }

                    String newFilePathWithoutDirPathString = filePathWithoutDirPathString.replaceAll(".dat", "." + fileTypeEnum.name());
                    String outputFilePathString = "D:/TEMP/output/success" + File.separator + newFilePathWithoutDirPathString;
                    FileUtils.decryptFile(filePathString,outputFilePathString,xorInteger);
                    Boolean judgeImageBoolean = ImageUtils.judgeImage(outputFilePathString);
                    if (!judgeImageBoolean){
                        boolean deleteFileBoolean = FileUtils.deleteFile(outputFilePathString);
                        if (!deleteFileBoolean){
                            throw new RuntimeException("Failed to delete file.");
                        }
                        continue;
                    }
                    successBoolean = true;
                    break;
                }
                if (!successBoolean){
                    String outputFilePathString = "D:/TEMP/output/fail"+ File.separator + filePathWithoutDirPathString;
                    FileUtils.copyFile(filePathString,outputFilePathString);
                }
            }
        }
    }
    @Test
    public void test6(){
        String uniString = WeChatUtils.getUniString("D:\\Study_Classification\\Hook\\EKS_Files\\LouYue_20190629_2013\\com_tencent_mm_db_output\\data\\data\\com.tencent.mm\\MicroMsg\\systemInfo.cfg");
        String imeiString = WeChatUtils.getImeiString("D:\\Study_Classification\\Hook\\EKS_Files\\LouYue_20190629_2013\\com_tencent_mm_db_output\\data\\data\\com.tencent.mm\\MicroMsg\\CompatibleInfo.cfg");
        String weChatKeyString = WeChatUtils.getWeChatKeyString(imeiString, uniString);
        System.out.println("uniString:" + uniString);
        System.out.println("imeiString:" + imeiString);
        System.out.println("weChatKeyString:" + weChatKeyString);
    }
    @Test
    public void test7(){

    }
}
