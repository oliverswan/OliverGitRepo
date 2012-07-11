package net.oliver.j2se.stream.bytearray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


// 在网络传输中我们往往要传输很多变量，我们可以利用ByteArrayOutputStream把所有的变量收集到一起，然后一次性把数据发送出去
public class test {
 public static void main(String[] args) {
  int a=0;
  int b=1;
  int c=2;
  
  ByteArrayOutputStream bout = new ByteArrayOutputStream();
  bout.write(a);
  bout.write(b);
  bout.write(c);
  
  byte[] buff = bout.toByteArray();
  for(int i=0; i<buff.length; i++)
   System.out.println(buff[i]);
  System.out.println("***********************");
  
  
  ByteArrayInputStream bin = new ByteArrayInputStream(buff);
  // 每次读取一个字节
  while((b=bin.read())!=-1) {
   System.out.println(b);
  }
 }
}
