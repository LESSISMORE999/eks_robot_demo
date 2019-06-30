package test.com.eks.other;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
    @Test
    public void test1() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println("memory: " + unsafe.getByte(0x1b126c));
    }
}
