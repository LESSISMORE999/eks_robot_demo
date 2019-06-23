package com.eks.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static Boolean judgeImage(String imageFilePathString){
        File file = new File(imageFilePathString);
        if (!file.exists()){
            throw new RuntimeException("File does not exist.");
        }
        try {
            Image image = ImageIO.read(file);
            if (image == null){
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
