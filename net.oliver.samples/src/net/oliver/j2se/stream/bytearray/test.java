package net.oliver.j2se.stream.bytearray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


// �����紫������������Ҫ����ܶ���������ǿ�������ByteArrayOutputStream�����еı����ռ���һ��Ȼ��һ���԰����ݷ��ͳ�ȥ
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
  // ÿ�ζ�ȡһ���ֽ�
  while((b=bin.read())!=-1) {
   System.out.println(b);
  }
 }
}
