package net.oliver.j2se.classloader;  
  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
  
/** 
 * 深入JVM之自定义类加载器 
 * @author 宏宇 
 * @editor Jan 25, 2012 6:14:44 PM 
 */  
public class MyClassLoader extends ClassLoader {  
   private String name;                      //类加载器的名字   
   private String path = "D:\\";             //加载类的路径   
   private final String fileType = ".class"; //class文件的扩展名   
     
   public MyClassLoader(String name){  
       super(); //让系统类加载器成为该类加载器的父加载器   
       this.name = name;  
   }  
     
   public MyClassLoader(ClassLoader parent, String name){  
       super(parent); //显式指定该类加载器的父加载器   
       this.name = name;  
   }  
     
   /** 
    * 根据类名，得到class文件的二进制的字节数组 
    * @author 宏宇 
    * @editor Jan 25, 2012 6:22:39 PM 
    * @see 返回:因为类里面的数据本身是二进制代码，所以该方法的返回值必须定义成byte[]的形式 
    * @see 思路:从硬盘上通过输入流把二进制数据加载到内存，然后把它输出到一个字节数组输出流中 
    * @see 思路:然后在字节数组输出流里面将其转换为一个字节数组，赋给data，最后返回 
    */  
   private byte[] loadClassData(String name){  
       InputStream is = null;  
       byte[] data = null; //最终所返回的字节数组   
       ByteArrayOutputStream baos = null;  
         
       try{  
           name = name.replace(".", "\\"); //把点替换成斜杠   
           is = new FileInputStream(new File(path + name + fileType));  
           baos = new ByteArrayOutputStream();  
           int ch = 0;  
           while(-1 != (ch=is.read())){  
               baos.write(ch);  
           }  
           data = baos.toByteArray(); //转换为字节数组   
       }catch(FileNotFoundException e){  
           e.printStackTrace();  
       }catch(IOException e){  
           e.printStackTrace();  
       }finally{  
           try{  
               is.close();  
               baos.close();  
           }catch(IOException e){  
               e.printStackTrace();  
           }  
       }  
       return data;  
   }  
 
   /** 
    * 该方法会被我们自定义的类加载器MyClassLoader.loadClass()方法自动调用 
    */  
   @Override  
   protected Class<?> findClass(String name) throws ClassNotFoundException {  
       byte[] data = this.loadClassData(name);  
       return this.defineClass(name, data, 0, data.length); //将字节数组转换为一个class类的实例   
   }  
     
   @Override  
   public String toString() {  
       return this.name;  
   }  
     
   public String getPath() {  
       return path;  
   }  
   public void setPath(String path) {  
       this.path = path;  
   }  
     
   public static void main(String[] args) throws Exception {  
       MyClassLoader loader11 = new MyClassLoader("loader11"); //这表示loader11的父加载器是系统类加载器   
       loader11.setPath("D:\\tmp\\"); 
       MyClassLoader loader22 = new MyClassLoader(loader11, "loader22"); //将loader11作为loader22的父加载器   
       loader22.setPath("D:\\tmp\\");  
       MyClassLoader loader33 = new MyClassLoader(null, "loader33"); //null表示loader33的父加载器是根类加载器   
       loader33.setPath("D:\\tmp\\");  
       /** 
        * 如果MyAnimal类在load22的命名空间中还没有被加载，load22首先委托它的父加载器load11代为加载 
        * 
        * load11再请求系统类加载器代为加载，系统类加载器再请求扩展类加载器代为加载，扩展类加载器再请求根类加载器代为加载 
        * 
        * 根类加载器会从系统属性sun.boot.class.path所指定的目录中加载类库 
        * 扩展类加载器会从系统属性java.ext.dirs所指定的目录，以及，JDK安装目录的jre//lib//ext子目录(扩展目录)中加载类库 
        * 系统类加载器会从系统属性java.class.path或者环境变量classpath所指定的目录中加载类
        *  
        * 结果根、扩展类加载器均不能加载，则系统类加载器尝试加载，于是加载成功，便将MyAnimal类所对应的Class对象的引用返回 
        * 
        * 补充：MyAnimal中主动使用了MyDog类，当执行MyAnimal类的构造方法中的new MyDog()语句时 
        *      JVM会使用MyAnimal类的定义类加载器去加载MyDog类，加载过程同样采用父亲委托机制 
        * 
        * 注意：当系统类加载器尝试加载该类时，它会在classpath路径下查找MyAnimal类，找到则尝试加载，找不到则交由load11去加载 
        * 所以，我们可以移动一下MyAnimal和MyPig的class文件位置，再运行"java MyClassLoader"命令，查看控制台打印结果 
        */  
        loader22.loadClass("com.jadyer.classloader.MyAnimal").newInstance();  
        System.out.println("=================================================================");  
        /** 
         * 同理，此处首先会发现MyAnimal在load33的命名空间中还没有被加载，load33首先委托它的父加载器根类加载器代为加载 
         * 结果根类加载器无法加载该类，于是load33尝试加载，加载成功，于是返回了MyAnimal类所对应的Class对象的引用 
         */  
        loader33.loadClass("com.jadyer.classloader.MyAnimal").newInstance();  
        System.out.println("=================================================================");         
    }  
}  
