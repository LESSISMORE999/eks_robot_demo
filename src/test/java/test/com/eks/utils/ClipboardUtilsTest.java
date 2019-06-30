package test.com.eks.utils;

import com.eks.utils.ClipboardUtils;
import org.junit.Test;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUtilsTest {
    @Test
    public void test1() throws IOException, UnsupportedFlavorException {
        ClipboardUtils.setClipboardText("HelloWorld!");
        System.out.println(ClipboardUtils.getTextFromClipboard());
    }
    @Test
    public void test2() throws Exception {
        ClipboardUtils.setClipboardImage("D:\\20190112_1105_GitHub_NameSpace\\eks_robot_demo\\src\\main\\resources\\wechat\\3f9d3219e813471212e91b1e429c8f8f.jpg");
    }
}
