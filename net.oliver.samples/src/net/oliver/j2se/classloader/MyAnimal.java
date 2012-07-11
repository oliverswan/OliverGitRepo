package net.oliver.j2se.classloader;  
  
/** 
 * MyAnimal 
 * @author 宏宇 
 * @editor Jan 25, 2012 8:46:28 PM 
 */  
public class MyAnimal {  
    public int v1 = 1;  
    public MyAnimal(){  
        System.out.println("MyAnimal is loaded by: " + this.getClass().getClassLoader());  
        new MyPig(); //主动使用Pig类   
    }  
}  
