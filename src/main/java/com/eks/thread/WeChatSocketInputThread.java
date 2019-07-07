package com.eks.thread;

import com.eks.thread.base.SocketInputThread;

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
            }
        } catch (Exception e) {
        }
    }
}
