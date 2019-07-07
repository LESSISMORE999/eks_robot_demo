package com.eks.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor//全参构造器
public enum FileTypeEnum {
    jpg("FFD8FF"),png("89504E47");
    private String startHexString;
}
