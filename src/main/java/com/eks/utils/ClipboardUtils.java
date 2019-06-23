package com.eks.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class ClipboardUtils {
    private static Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    public static void setClipboardText(String contentString) {
        Transferable transferable = new StringSelection(contentString);
        CLIPBOARD.setContents(transferable, null);
    }
    public static String getTextFromClipboard() throws IOException, UnsupportedFlavorException {
        Transferable transferable = CLIPBOARD.getContents(null);
        if (transferable == null || !transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            throw new RuntimeException("Clipboard content is not String.");
        }
        return (String) transferable.getTransferData(DataFlavor.stringFlavor);
    }
    public static Image getImageFromClipboard() throws Exception {
        Transferable transferable = CLIPBOARD.getContents(null);
        if (transferable == null || !transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            throw new RuntimeException("Clipboard content is not Image.");
        }
        return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
    }
    public static void setClipboardImage(final Image image){
        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.imageFlavor };
            }
            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }
            @Override
            public Object getTransferData(DataFlavor flavor)throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) {
                    throw new UnsupportedFlavorException(flavor);
                }
                return image;
            }
        };
        CLIPBOARD.setContents(transferable,null);
    }
    //TODO:Remain to be improved
    public void getImageAndTextFromClipboard() throws Exception{
        Transferable transferable = CLIPBOARD.getContents(null);
        DataFlavor[] dataFlavorArray = transferable.getTransferDataFlavors();
        int wholeLength = 0;
        for (int i = 0; i < dataFlavorArray.length; i++) {
            DataFlavor dataFlavor = dataFlavorArray[i];
            if (dataFlavor.getSubType().equals("rtf")) {
                Reader reader = dataFlavor.getReaderForText(transferable);
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("d:\\test.rtf"));
                char[] c = new char[1024];
                int leng = -1;
                while ((leng = reader.read(c)) != -1) {
                    osw.write(c, wholeLength, leng);
                }
                osw.flush();
                osw.close();
            }
        }
    }
}