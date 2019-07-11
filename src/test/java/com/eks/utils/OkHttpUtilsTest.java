package com.eks.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

public class OkHttpUtilsTest {
    @Test
    public void test1() throws IOException {
        JsonObject responseJsonObject = OkHttpUtils.sendRequest("https://www.apiopen.top/journalismApi").getAsJsonObject();
        JsonObject dataJsonObject = responseJsonObject.get("data").getAsJsonObject();
        JsonArray toutiaoJsonArray = dataJsonObject.get("toutiao").getAsJsonArray();
        JsonElement jsonElement = GsonUtils.generateJsonElementWithNeededField(toutiaoJsonArray, "title");
        System.out.println(jsonElement);
    }
    @Test
    public void test2() throws IOException {
        JsonArray responseJsonArray = OkHttpUtils.sendRequest("http://api.laifudao.com/open/xiaohua.json").getAsJsonArray();
        System.out.println(responseJsonArray);
        JsonElement jsonElement = GsonUtils.generateJsonElementWithNeededField(responseJsonArray, "title", "content");
        System.out.println(jsonElement);
    }
    @Test
    public void test3() throws IOException {
        JsonObject requestJsonObject = new JsonObject();
        requestJsonObject.addProperty("reqType",0);

        JsonObject perceptionJsonObject = new JsonObject();
        JsonObject inputTextJsonObject = new JsonObject();
        inputTextJsonObject.addProperty("text","你好");
        perceptionJsonObject.add("inputText",inputTextJsonObject);
        requestJsonObject.add("perception",perceptionJsonObject);

        JsonObject userInfoJsonObject = new JsonObject();
        userInfoJsonObject.addProperty("apiKey","23b51121ba4b42e4a743bd49cba7986f");
        userInfoJsonObject.addProperty("userId","998");
        requestJsonObject.add("userInfo",userInfoJsonObject);

        JsonObject jsonObject = OkHttpUtils.sendTuLingRobotRequest(requestJsonObject);
        System.out.println(jsonObject.toString());
        JsonObject resultJsonObject = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject();
        String string = resultJsonObject.get("values").getAsJsonObject().get("text").getAsString();
        System.out.println(string);
    }
}
