package net.oliver.j2se.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池导致的OOM异常
 * 
 * 要在常量池里添加内容，最简单的就是使用String.intern()这个Native方法
 * 
 * 由于常量池分配在方法区内，我们只需要通过-XX:PermSize和-XX:MaxPermSize限制方法区大小即可限制常量池容量
 * 
 * VM参数：-XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPoolOOM {
 
       public static void main(String[] args) {
              // 使用List保持着常量池引用，压制Full GC回收常量池行为
              List<String> list = new ArrayList<String>();
              // 10M的PermSize在integer范围内足够产生OOM了
              int i = 0;
              while (true) {
            	  	 // 如果池已经包含一个等于此 String 对象的字符串（该对象由 equals(Object) 方法确定），
            	  	 // 则返回池中的字符串。否则，将此 String 对象添加到池中，并且返回此 String 对象的引用
            	  	 // 添加到方法区的常量池中
                     list.add(String.valueOf(i++).intern());//个人认为在此种特殊情况下，你只要创建了新的String对象常量池都会产生新的对象，是否调用intern方法是不会有影响的，因为intern方法会查找常量池中已经存在的字符串，如果有存在就直接返回，如果不存在才会进一步锁住常量池进行添加的行为   
              }
       }
}

// 如果不是调用intern的话
/*Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
at java.lang.Integer.toString(Integer.java:306)
at java.lang.Integer.toString(Integer.java:116)
at java.lang.String.valueOf(String.java:2932)
at net.oliver.j2se.memory.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java:22)*/

/*
Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
	at java.lang.String.intern(Native Method)
	at net.oliver.j2se.memory.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java:21)
*/
