package com.eks.enumeration;

public enum FileTypeEnum {
    jpg("FFD8FF"),png("89504E47");
    private String startHexString;
    FileTypeEnum(String startHexString){
        this.startHexString = startHexString;
    }
    public String getStartHexString() {
        return startHexString;
    }
}
