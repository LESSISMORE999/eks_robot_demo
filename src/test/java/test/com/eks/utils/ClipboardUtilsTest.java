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
}
