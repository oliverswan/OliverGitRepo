package net.oliver.j2se.classloader;  
  
/** 
 * MyPig 
 * @author ∫Í”Ó 
 * @editor Jan 25, 2012 8:46:34 PM 
 */  
public class MyPig {  
    public MyPig(){  
        System.out.println("   MyPig is loaded by: " + this.getClass().getClassLoader());  
    }  
}  
