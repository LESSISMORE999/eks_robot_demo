package com.eks.utils;

import com.eks.thread.WeChatSocketOutputThread;
import com.eks.thread.WeChatSocketThread;
import org.junit.Test;

import java.net.Socket;

public class SocketUtilsTest {
    private Integer portInteger = 8899;
    @Test
    public void test1() throws Exception {
        SocketUtils.createSocketServer(WeChatSocketThread.class,portInteger);
    }
    @Test
    public void test2() throws Exception {
        Socket socket = SocketUtils.createSocketClient(portInteger);
        String contentString = "Hello,this is a test message.";
        contentString = StringUtils.convertStringByCharsetString(contentString, "UTF-8", "GBK");
        System.out.println(contentString);
        WeChatSocketOutputThread weChatSocketOutputThread = new WeChatSocketOutputThread(socket.getOutputStream(),contentString);
        weChatSocketOutputThread.start();
        weChatSocketOutputThread.join();
        socket.close();
    }
}
