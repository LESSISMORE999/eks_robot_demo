package test.com.eks.other;

import com.eks.utils.ClipboardUtils;
import com.eks.utils.GsonUtils;
import com.eks.utils.OkHttpUtils;
import com.eks.utils.RobotUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

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
}
