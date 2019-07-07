package com.eks.other;

import com.eks.pojo.MessagePojo;
import com.google.gson.Gson;
import org.junit.Test;

public class StringTest {
    @Test
    public void test1(){
        String messageString = "MessageString:Messg__________测试__________16925916487@chatroom__________wxid_lkba0rl8rdhp22__________1__________0";
        String[] splitStringArray = messageString.split("__________");
        System.out.println(new Gson().toJson(splitStringArray));
        MessagePojo messagePojo = new MessagePojo();
        messagePojo.setMessageString(splitStringArray[1]);
        messagePojo.setMessageSourceType(splitStringArray[5]);
        messagePojo.setMessageType(splitStringArray[4]);
        messagePojo.setSenderOrChatRoomId(splitStringArray[2]);
        messagePojo.setChatRoomSenderId(splitStringArray[3]);
        System.out.println(new Gson().toJson(messagePojo));
    }
}
