package com.eks.utils;

import java.lang.reflect.Field;
  
import sun.misc.Unsafe;  
  
public class DirectMemoryAccess {  
  
    public static void main(String[] args) {  
  
        /* 
         * Unsafe的构造函数是私有的，不能通过new来获得实例。 
         *  
         *  通过反射来获取 
         */  
        Unsafe unsafe = null;  
        Field field = null;  
        try {  
            field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");  
            /* 
             * private static final Unsafe theUnsafe = new Unsafe(); 
             *  
             * 因为field的修饰符为 private static final, 
             * 需要将setAccessible设置成true，否则会报java.lang.IllegalAccessException 
             */  
            field.setAccessible(true);  
            unsafe = (Unsafe) field.get(null);  
        } catch (SecurityException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        long oneHundred = 123456781;
        byte size = 4;
  
        /* 
         * 调用allocateMemory分配内存 
         */  
        long memoryAddress = unsafe.allocateMemory(size);

  
        /* 
         * 将100写入到内存中 
         */
        unsafe.putAddress(memoryAddress, oneHundred);
        System.out.println(memoryAddress);
        /* 
         * 内存中读取数据  
         */  
//        long readValue = unsafe.getAddress(memoryAddress);
        long readValue = unsafe.getAddress(memoryAddress);
        unsafe.putAddress(memoryAddress, oneHundred + 1);
        unsafe.putAddress(memoryAddress, oneHundred + 1 +1);
        System.out.println("Val : " + readValue);
    }  
}  