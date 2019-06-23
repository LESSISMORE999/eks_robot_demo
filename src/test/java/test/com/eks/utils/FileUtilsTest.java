package test.com.eks.utils;

import com.eks.enumeration.FileTypeEnum;
import com.eks.utils.FileUtils;
import com.eks.utils.ImageUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtilsTest {
    String filePathString = "D:\\20190112_1105_GitHub_NameSpace\\eks_robot_demo\\src\\main\\resources\\wechat\\3f9d3219e813471212e91b1e429c8f8f.dat";
    String filePathString2 = "D:\\20190112_1105_GitHub_NameSpace\\eks_robot_demo\\src\\main\\resources\\wechat\\3f9d3219e813471212e91b1e429c8f8f.jpg";
    String filePathString3 = "D:\\test.png";
    @Test
    public void test1() throws IOException {
        String fileHexString = FileUtils.fileToHexString(filePathString2);
        System.out.println(fileHexString);
        System.out.println(FileUtils.getFileRealTypeString(filePathString2));
    }
    @Test
    public void test2() {
        String binaryString = FileUtils.hexStringToBinaryString("89504E");
        System.out.println(binaryString);
        String hexString = FileUtils.binaryStringToHexString(binaryString);
        System.out.println(hexString);
    }
    @Test
    public void test3() {
        Integer integer1 = FileUtils.hexStringToInteger("89");
        Integer integer2 = FileUtils.hexStringToInteger("89");
        int i = integer1 ^ integer2;
        System.out.println(FileUtils.integerToHexString(i));
    }
    @Test
    public void test4() throws IOException {
        String fileHexString = FileUtils.fileToHexString(filePathString);
        String substring = fileHexString.substring(0, 2);
        System.out.println(substring);
        Integer integer1 = FileUtils.hexStringToInteger(substring);
        Integer integer2 = FileUtils.hexStringToInteger("FF");
        int integer = integer1 ^ integer2;
        System.out.println(integer);
        System.out.println(FileUtils.integerToHexString(integer));
    }
    @Test
    public void test5() throws IOException {
        String dirPathString = "D:\\TEMP\\input";
        List<String> filePathStringList = FileUtils.getAllFilePathBaseDir(dirPathString);
        for(String filePathString : filePathStringList){
            if (filePathString.endsWith(".dat")){
                String filePathWithoutDirPathString = filePathString.substring(dirPathString.length(), filePathString.length());

                String fileHexString = FileUtils.fileToHexString(filePathString);
                String substring = fileHexString.substring(0, 2);
                Integer integer1 = FileUtils.hexStringToInteger(substring);

                FileTypeEnum[] fileTypeEnumArray = FileTypeEnum.values();
                Boolean successBoolean = false;
                for(FileTypeEnum fileTypeEnum : fileTypeEnumArray){
                    String fileTypeEnumStartHexString = fileTypeEnum.getStartHexString();
                    String fileTypeEnumStartHexStringSubstring = fileTypeEnumStartHexString.substring(0, 2);
                    Integer integer2 = FileUtils.hexStringToInteger(fileTypeEnumStartHexStringSubstring);
                    int xorInteger = integer1 ^ integer2;
                    String newFilePathWithoutDirPathString = filePathWithoutDirPathString.replaceAll(".dat", "." + fileTypeEnum.name());
                    String outputFilePathString = "D:/TEMP/output/success" + File.separator + newFilePathWithoutDirPathString;
                    FileUtils.decryptFile(filePathString,outputFilePathString,xorInteger);
                    Boolean judgeImageBoolean = ImageUtils.judgeImage(outputFilePathString);
                    if (judgeImageBoolean){
                        successBoolean = true;
                        break;
                    }
                    FileUtils.deleteFile(outputFilePathString);
                }
                if (!successBoolean){
                    String outputFilePathString = "D:/TEMP/output/fail"+ File.separator + filePathWithoutDirPathString;
                    FileUtils.copyFile(filePathString,outputFilePathString);
                }
            }
        }
    }
}
