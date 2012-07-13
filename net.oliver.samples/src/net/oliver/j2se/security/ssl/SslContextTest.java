package net.oliver.j2se.security.ssl;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
使用默认SSL socket factories创建的SSL sockets没有任何证书被绑定来进行验证。

为了将证书绑定给SSL sockets,需要使用 SSLContext类来创建SSL socket factories. 

Here is a sample program, SslContextTest.java:

Of course, to run this program, you need to have the key store file, herong.jks, ready. 

It contains a self-signed pair of private and public keys. 

Read my notes on "JCA - Certificates, 'keytool' and 'keystore'", 
if you want to use "keytool" to create such a key store file. 

*/
	
public class SslContextTest {
	 public static void main(String[] args) {
		 
	      PrintStream out = System.out;
	      out.println("\nTesting socket factory with SSLContext:");
	      try {
	      	 // SSLContext protocols: TLS, SSL, SSLv3
	         SSLContext sc = SSLContext.getInstance("SSLv3");
	         System.out.println("\nSSLContext class: "+sc.getClass());
	         System.out.println("   Protocol: "+sc.getProtocol());
	         System.out.println("   Provider: "+sc.getProvider());

	      	 // SSLContext algorithms: SunX509
	         KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	         System.out.println("\nKeyManagerFactory class: " +kmf.getClass());
	         System.out.println("   Algorithm: "+kmf.getAlgorithm());
	         System.out.println("   Provider: "+kmf.getProvider());

	      	 // KeyStore types: JKS
	         String ksName = "herong.jks";
	         char ksPass[] = "HerongJKS".toCharArray();
	         char ctPass[] = "My1stKey".toCharArray();
	         KeyStore ks = KeyStore.getInstance("JKS");
	         // 加载keystore: jks 文件
	         ks.load(new FileInputStream(ksName), ksPass);
	         System.out.println("\nKeyStore class: "+ks.getClass());
	         System.out.println("   Type: "+ks.getType());
	         System.out.println("   Provider: "+ks.getProvider());
	         System.out.println("   Size: "+ks.size());

	         // Generating KeyManager list
	         kmf.init(ks,ctPass);
	         KeyManager[] kmList = kmf.getKeyManagers();
	         System.out.println("\nKeyManager class: " +kmList[0].getClass());
	         System.out.println("   # of key manager: " +kmList.length);

	         // 生成 SSLServerSocketFactory
	         sc.init(kmList, null, null);
	         SSLServerSocketFactory ssf = sc.getServerSocketFactory();
	         System.out.println("\nSSLServerSocketFactory class: "
	            +ssf.getClass());
	         
	         // 生成 SSLServerSocket
	         SSLServerSocket ss  = (SSLServerSocket) ssf.createServerSocket();
	         System.out.println("\nSSLServerSocket class: "  +ss.getClass());
	         System.out.println("   String: "+ss.toString());

	         // 生成 SSLSocketFactory
	         sc.init(kmList, null, null);
	         SSLSocketFactory sf = sc.getSocketFactory();
	         System.out.println("\nSSLSocketFactory class: "
	            +sf.getClass());

	         // 生成 SSLSocket
	         SSLSocket s  = (SSLSocket) sf.createSocket();
	         System.out.println("\nSSLSocket class: "+s.getClass());
	         System.out.println("   String: "+s.toString());
	      } catch (Exception e) {
	         System.err.println(e.toString());
	      }
	   }
}
