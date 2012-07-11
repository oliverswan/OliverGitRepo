package net.oliver.j2se.memory;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 方法区用于存放Class相关信息，所以这个区域的测试我们借助CGLib直接操作字节码动态生成大量的Class
 * 这里我们这个例子中模拟的场景其实经常会在实际应用中出现：当前很多主流框架，如Spring、Hibernate对类进行增强时，都会使用到CGLib这类字节码技术，
 * 当增强的类越多，就需要越大的方法区用于保证动态生成的Class可以加载入内存
 * 
 * VM Args： -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author zzm
 */
public class JavaMethodAreaOOM {
 
       public static void main(String[] args) {
              while (true) {
                     Enhancer enhancer = new Enhancer();
                     enhancer.setSuperclass(OOMObject.class);
                     enhancer.setUseCache(false);
                     enhancer.setCallback(new MethodInterceptor() {
                            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                                   return proxy.invokeSuper(obj, args);
                            }
                     });
                     // 持续创建Class
                     enhancer.create();
              }
       }
 
       static class OOMObject {
 
       }
}

/*Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
at java.lang.Class.getDeclaredMethods0(Native Method)
at java.lang.Class.privateGetDeclaredMethods(Class.java:2427)
at java.lang.Class.getDeclaredMethod(Class.java:1935)
at net.sf.cglib.proxy.Enhancer.getCallbacksSetter(Enhancer.java:627)
at net.sf.cglib.proxy.Enhancer.setCallbacksHelper(Enhancer.java:615)
at net.sf.cglib.proxy.Enhancer.setThreadCallbacks(Enhancer.java:609)
at net.sf.cglib.proxy.Enhancer.createUsingReflection(Enhancer.java:631)
at net.sf.cglib.proxy.Enhancer.firstInstance(Enhancer.java:538)
at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:225)
at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:377)
at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:285)
at net.oliver.j2se.memory.JavaMethodAreaOOM.main(JavaMethodAreaOOM.java:29)*/
