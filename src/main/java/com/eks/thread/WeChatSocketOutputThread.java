package com.eks.thread;

import com.eks.thread.base.SocketOutputThread;

import java.io.OutputStream;

public class WeChatSocketOutputThread extends SocketOutputThread {
    public WeChatSocketOutputThread(OutputStream outputStream, String messageString) {
        super(outputStream, messageString);
    }
    @Override
    public void run() {
        try {
            OutputStream outputStream = this.getOutputStream();
            String messageString = this.getMessageString();
            byte[] sendBytes = messageString.getBytes("UTF-8");
            int divideHundredInt = sendBytes.length / 100;
            int remainderInt = sendBytes.length - (divideHundredInt * 100);
            outputStream.write(divideHundredInt);
            outputStream.write(remainderInt);
            outputStream.write(sendBytes);
            outputStream.flush();
        } catch (Exception e) {
        }
    }
}
