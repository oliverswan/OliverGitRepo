package net.oliver.j2se.classloader;

import java.io.File;   
import java.io.PrintStream;   
import java.lang.reflect.Field;   
import java.lang.reflect.Method;   
import java.net.MalformedURLException;   
import java.net.URL;   
import java.net.URLClassLoader;   
import java.util.List;   
 
import sun.misc.Launcher;   
  
public class ClassLoaderUtil {   
	
   private static Field classes;   // classloader用来持有已经加载的来，来防止被GC，直到classloader被gc
 
   private static Method addURL;   // 添加查找位置URL的方法
   
   static {   
       try {   
           classes = ClassLoader.class.getDeclaredField("classes");   
           addURL = URLClassLoader.class.getDeclaredMethod("addURL",   
                   new Class[] { URL.class });   
       } catch (Exception e) {   
           e.printStackTrace();   
       }   
       classes.setAccessible(true);  // 禁止java安全检查 
       addURL.setAccessible(true);  // 禁止java安全检查 
   }   
 
   // system
   private static URLClassLoader system = (URLClassLoader) getSystemClassLoader();  
   // ext
   private static URLClassLoader ext = (URLClassLoader) getExtClassLoader();   
 
   public static ClassLoader getSystemClassLoader() {   
       return ClassLoader.getSystemClassLoader();   
   }   
 
   public static ClassLoader getExtClassLoader() {   
       return getSystemClassLoader().getParent();   
   }   
 
   /**  
    * 获得加载的类  
    *   
    * @return  
    */  
   public static List getClassesLoadedBySystemClassLoader() {   
       return getClassesLoadedByClassLoader(getSystemClassLoader());   
   }   
 
   public static List getClassesLoadedByExtClassLoader() {   
       return getClassesLoadedByClassLoader(getExtClassLoader());   
   }   
 
   // 得到传入的classloader所已经加载的类的List
   public static List getClassesLoadedByClassLoader(ClassLoader cl) {   
       try {   
           return (List) classes.get(cl);   
       } catch (Exception e) {   
           e.printStackTrace();   
       }   
       return null;   
   }   
   
   public static void main(String[] args)
   {
	   URL[] urls = getBootstrapURLs();
	   for(URL url : urls)
	   {
		   System.out.println(url);
	   }
	   
	   
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/resources.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/rt.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/sunrsasign.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/jsse.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/jce.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/lib/charsets.jar
//	   file:/D:/Program%20Files/Dev/Java/jdk1.6.0_10/jre/classes

   }
 
   // 得到bootstrap类路径
   public static URL[] getBootstrapURLs() {   
       return Launcher.getBootstrapClassPath().getURLs();   
   }   
 
   // 得到系统类路径
   public static URL[] getSystemURLs() {   
       return system.getURLs();   
   }   
 
   // 得到扩展类路径
   public static URL[] getExtURLs() {   
       return ext.getURLs();   
   }   
 
   private static void list(PrintStream ps, URL[] classPath) {   
       for (int i = 0; i < classPath.length; i++) {   
           ps.println(classPath[i]);   
       }   
   }   
 
   public static void listBootstrapClassPath() {   
       listBootstrapClassPath(System.out);   
   }   
 
   public static void listBootstrapClassPath(PrintStream ps) {   
       ps.println("BootstrapClassPath:");   
       list(ps, getBootstrapClassPath());   
   }   
 
   public static void listSystemClassPath() {   
       listSystemClassPath(System.out);   
   }   
 
   public static void listSystemClassPath(PrintStream ps) {   
       ps.println("SystemClassPath:");   
       list(ps, getSystemClassPath());   
   }   
 
   public static void listExtClassPath() {   
       listExtClassPath(System.out);   
    }   
  
    public static void listExtClassPath(PrintStream ps) {   
        ps.println("ExtClassPath:");   
        list(ps, getExtClassPath());   
    }   
  
    public static URL[] getBootstrapClassPath() {   
        return getBootstrapURLs();   
    }   
  
    public static URL[] getSystemClassPath() {   
        return getSystemURLs();   
    }   
  
    public static URL[] getExtClassPath() {   
        return getExtURLs();   
    }   
  
    public static void addURL2SystemClassLoader(URL url) {   
        try {   
            addURL.invoke(system, new Object[] { url });   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    public static void addURL2ExtClassLoader(URL url) {   
        try {   
            addURL.invoke(ext, new Object[] { url });   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    public static void addClassPath(String path) {   
        addClassPath(new File(path));   
    }   
  
    public static void addExtClassPath(String path) {   
        addExtClassPath(new File(path));   
    }   
  
    public static void addClassPath(File dirOrJar) {   
        try {   
            addURL2SystemClassLoader(dirOrJar.toURL());   
        } catch (MalformedURLException e) {   
            e.printStackTrace();   
        }   
    }   
  
    public static void addExtClassPath(File dirOrJar) {   
        try {   
            addURL2ExtClassLoader(dirOrJar.toURL());   
        } catch (MalformedURLException e) {   
            e.printStackTrace();   
        }   
    }   
  
} 