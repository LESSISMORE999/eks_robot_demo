package test.com.eks.utils;

import com.eks.utils.GsonUtils;
import com.eks.utils.OkHttpUtils;
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
}
