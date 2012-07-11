package net.oliver.j2se.classloader;  
  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
  
/** 
 * ����JVM֮�Զ���������� 
 * @author ���� 
 * @editor Jan 25, 2012 6:14:44 PM 
 */  
public class MyClassLoader extends ClassLoader {  
   private String name;                      //�������������   
   private String path = "D:\\";             //�������·��   
   private final String fileType = ".class"; //class�ļ�����չ��   
     
   public MyClassLoader(String name){  
       super(); //��ϵͳ���������Ϊ����������ĸ�������   
       this.name = name;  
   }  
     
   public MyClassLoader(ClassLoader parent, String name){  
       super(parent); //��ʽָ������������ĸ�������   
       this.name = name;  
   }  
     
   /** 
    * �����������õ�class�ļ��Ķ����Ƶ��ֽ����� 
    * @author ���� 
    * @editor Jan 25, 2012 6:22:39 PM 
    * @see ����:��Ϊ����������ݱ����Ƕ����ƴ��룬���Ը÷����ķ���ֵ���붨���byte[]����ʽ 
    * @see ˼·:��Ӳ����ͨ���������Ѷ��������ݼ��ص��ڴ棬Ȼ����������һ���ֽ������������ 
    * @see ˼·:Ȼ�����ֽ�������������潫��ת��Ϊһ���ֽ����飬����data����󷵻� 
    */  
   private byte[] loadClassData(String name){  
       InputStream is = null;  
       byte[] data = null; //���������ص��ֽ�����   
       ByteArrayOutputStream baos = null;  
         
       try{  
           name = name.replace(".", "\\"); //�ѵ��滻��б��   
           is = new FileInputStream(new File(path + name + fileType));  
           baos = new ByteArrayOutputStream();  
           int ch = 0;  
           while(-1 != (ch=is.read())){  
               baos.write(ch);  
           }  
           data = baos.toByteArray(); //ת��Ϊ�ֽ�����   
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
    * �÷����ᱻ�����Զ�����������MyClassLoader.loadClass()�����Զ����� 
    */  
   @Override  
   protected Class<?> findClass(String name) throws ClassNotFoundException {  
       byte[] data = this.loadClassData(name);  
       return this.defineClass(name, data, 0, data.length); //���ֽ�����ת��Ϊһ��class���ʵ��   
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
       MyClassLoader loader11 = new MyClassLoader("loader11"); //���ʾloader11�ĸ���������ϵͳ�������   
       loader11.setPath("D:\\tmp\\"); 
       MyClassLoader loader22 = new MyClassLoader(loader11, "loader22"); //��loader11��Ϊloader22�ĸ�������   
       loader22.setPath("D:\\tmp\\");  
       MyClassLoader loader33 = new MyClassLoader(null, "loader33"); //null��ʾloader33�ĸ��������Ǹ��������   
       loader33.setPath("D:\\tmp\\");  
       /** 
        * ���MyAnimal����load22�������ռ��л�û�б����أ�load22����ί�����ĸ�������load11��Ϊ���� 
        * 
        * load11������ϵͳ���������Ϊ���أ�ϵͳ���������������չ���������Ϊ���أ���չ�����������������������Ϊ���� 
        * 
        * ������������ϵͳ����sun.boot.class.path��ָ����Ŀ¼�м������ 
        * ��չ����������ϵͳ����java.ext.dirs��ָ����Ŀ¼���Լ���JDK��װĿ¼��jre//lib//ext��Ŀ¼(��չĿ¼)�м������ 
        * ϵͳ����������ϵͳ����java.class.path���߻�������classpath��ָ����Ŀ¼�м�����
        *  
        * ���������չ������������ܼ��أ���ϵͳ����������Լ��أ����Ǽ��سɹ����㽫MyAnimal������Ӧ��Class��������÷��� 
        * 
        * ���䣺MyAnimal������ʹ����MyDog�࣬��ִ��MyAnimal��Ĺ��췽���е�new MyDog()���ʱ 
        *      JVM��ʹ��MyAnimal��Ķ����������ȥ����MyDog�࣬���ع���ͬ�����ø���ί�л��� 
        * 
        * ע�⣺��ϵͳ����������Լ��ظ���ʱ��������classpath·���²���MyAnimal�࣬�ҵ����Լ��أ��Ҳ�������load11ȥ���� 
        * ���ԣ����ǿ����ƶ�һ��MyAnimal��MyPig��class�ļ�λ�ã�������"java MyClassLoader"����鿴����̨��ӡ��� 
        */  
        loader22.loadClass("com.jadyer.classloader.MyAnimal").newInstance();  
        System.out.println("=================================================================");  
        /** 
         * ͬ���˴����Ȼᷢ��MyAnimal��load33�������ռ��л�û�б����أ�load33����ί�����ĸ������������������Ϊ���� 
         * �������������޷����ظ��࣬����load33���Լ��أ����سɹ������Ƿ�����MyAnimal������Ӧ��Class��������� 
         */  
        loader33.loadClass("com.jadyer.classloader.MyAnimal").newInstance();  
        System.out.println("=================================================================");         
    }  
}  
