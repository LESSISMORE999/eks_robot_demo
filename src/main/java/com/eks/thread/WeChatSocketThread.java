package com.eks.thread;

import com.eks.thread.base.SocketThread;
import com.eks.utils.FileUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WeChatSocketThread extends SocketThread{
    @Override
    public void run() {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        WeChatSocketInputThread weChatSocketInputThread = null;
        WeChatSocketOutputThread weChatSocketOutputThread = null;
        try {
            socket = this.getSocket();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            if (inputStream != null) {
                weChatSocketInputThread = new WeChatSocketInputThread(inputStream);
                weChatSocketInputThread.start();
            }
            if (outputStream != null) {
                String messageString = "hello server.";
                weChatSocketOutputThread = new WeChatSocketOutputThread(socket.getOutputStream(), messageString);
                weChatSocketOutputThread.start();
            }
            if (weChatSocketInputThread != null) {
                weChatSocketInputThread.join();
            }
            if (weChatSocketOutputThread != null) {
                weChatSocketOutputThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FileUtils.closeStream(inputStream);
            FileUtils.closeStream(outputStream);
            FileUtils.closeStream(socket);
        }
    }
}
