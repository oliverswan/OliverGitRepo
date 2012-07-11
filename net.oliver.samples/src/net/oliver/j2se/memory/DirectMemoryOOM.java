package net.oliver.j2se.memory;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * 测试本机直接内存申请过大抛出OOM
 * 
 * VM参数：-Xmx20M -XX:MaxDirectMemorySize=10M
 * 
 */
public class DirectMemoryOOM {
 
       private static final int _1MB = 1024 * 1024;
 
       public static void main(String[] args) throws Exception {
    	   
    	   	  // 代码越过了DirectByteBuffer，直接通过反射获取Unsafe实例进行内存分配（
    	   	  // Unsafe类的getUnsafe()方法限制了只有引导类加载器才会返回实例，也就是基本上只有rt.jar里面的类的才能使用）
              Field unsafeField = Unsafe.class.getDeclaredFields()[0];
              unsafeField.setAccessible(true);
             
              Unsafe unsafe = (Unsafe) unsafeField.get(null);
              while (true) {
                     unsafe.allocateMemory(_1MB);
              }
       }
}
