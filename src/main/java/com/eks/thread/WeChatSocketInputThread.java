package com.eks.thread;

import com.eks.pojo.MessagePojo;
import com.eks.thread.base.SocketInputThread;
import com.eks.utils.ClipboardUtils;
import com.eks.utils.OkHttpUtils;
import com.eks.utils.RobotUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.awt.event.KeyEvent;
import java.io.InputStream;

public class WeChatSocketInputThread extends SocketInputThread{
    public WeChatSocketInputThread(InputStream inputStream) {
        super(inputStream);
    }
    @Override
    public void run() {
        try {
            InputStream inputStream = this.getInputStream();
            byte[] byteArray = null;
            int divideHundredInt = 0;
            int remainderInt = 0;
            int messageLengthInt = 0;
            while (true) {
                divideHundredInt = inputStream.read();
                //如果读取的值为-1,说明到了流的末尾,Socket已经被关闭了,此时将不能再去读取,需要退出
                if(divideHundredInt == -1){
                    break;
                }
                remainderInt = inputStream.read();
                messageLengthInt = divideHundredInt * 100 + remainderInt;
                byteArray = new byte[messageLengthInt];
                inputStream.read(byteArray);
                String messageString = new String(byteArray, "GBK");
                System.out.println("MessageString:" + messageString);
                String[] splitStringArray = messageString.split("__________");
                System.out.println(new Gson().toJson(splitStringArray));
                MessagePojo messagePojo = new MessagePojo();
                messagePojo.setMessageString(splitStringArray[1]);
                messagePojo.setMessageSourceType(splitStringArray[5]);
                messagePojo.setMessageType(splitStringArray[4]);
                messagePojo.setSenderOrChatRoomId(splitStringArray[2]);
                messagePojo.setChatRoomSenderId(splitStringArray[3]);
                if ("0".equals(messagePojo.getMessageSourceType())){
                    String contentMessageString = messagePojo.getMessageString();
                    System.out.println(contentMessageString);

                    JsonObject requestJsonObject = new JsonObject();
                    requestJsonObject.addProperty("reqType",0);

                    JsonObject perceptionJsonObject = new JsonObject();
                    JsonObject inputTextJsonObject = new JsonObject();
                    inputTextJsonObject.addProperty("text",contentMessageString);
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
                    Thread.sleep(1000);
                    ClipboardUtils.setClipboardText(string);
                    RobotUtils.clickMouse(500,500, 2277, 284);
                    RobotUtils.pressKey(500, false, KeyEvent.VK_CONTROL,KeyEvent.VK_V);
                    RobotUtils.pressKey(500, true,KeyEvent.VK_ENTER);
                }
            }
        } catch (Exception e) {
        }
    }
}
