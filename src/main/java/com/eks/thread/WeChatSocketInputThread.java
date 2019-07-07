package com.eks.thread;

import com.eks.pojo.MessagePojo;
import com.eks.thread.base.SocketInputThread;
import com.google.gson.Gson;

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
                    System.out.println(messagePojo.getMessageString());
                }
            }
        } catch (Exception e) {
        }
    }
}
