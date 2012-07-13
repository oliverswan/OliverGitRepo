package net.oliver.j2se.security.ssl;

import java.io.PrintStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * 握手阶段， server 发送公钥给客户端.
 * 
 * 客户端然后选用一个key，并使用服务端公钥加密，并发给服务端，服务端使用私钥解密这个key,
 * 
 * 这样两端都可以使用这个key了. 
 * 
 * 通讯时两端都使用这个对称key进行加解密。
 * 
 * JSSE (Java Secure Socket Extension) 提供SSL和TLS的java实现，通过下面的类:
 * 
 * javax.net.ssl.SSLServerSocket - 服务端
 * javax.net.ssl.SSLSocket - 客户端
 * javax.net.ssl.SSLServerSocketFactory - 用来创建SSLServerSocket
 * javax.net.ssl.SSLSocketFactory - 用来创建SSLSocket
 * javax.net.ssl.SSLContext - 描述一个安全通讯上下文，初始化后，可以用来创建 SSLServerSocketFactory 和 SSLSocketFactory 对象.
 * 
 * javax.net.ssl.KeyManager - 用来管理密钥资源，来验证本地SSLSocket,If no key material is available, the socket will be unable to present authentication credentials. 
 * 决定可以使用的一系列别名,基于显示的条件选择最好的别名，并获取该别名对应的key资源 
 * 
 * KeyManagers are created by either using a KeyManagerFactory, or by implementing one of the KeyManager subclasses. 
 * 
 * javax.net.ssl.KeyManagerFactory - 用来基于给定的KeyStore对象来创建KeyManager对象
 * 
 * Note that JSSE does support a number of cipher suites for the SSL record
 * protocol
 */
public class SslSocketTest {

	public static void main(String[] args) {
		PrintStream out = System.out;
		out.println("\nDefault SSL socket factory:");
		try {
			// Generating the default SSLServerSocketFactory
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			System.out.println("\nSSLServerSocketFactory class: "+ ssf.getClass());
			String[] dcsList = ssf.getDefaultCipherSuites();
			out.println("   Default cipher suites:");
			for (int i = 0; i < dcsList.length; i++) {
				out.println("      " + dcsList[i]);
			}

			// Genearting SSLServerSocket
			SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket();
			System.out.println("\nSSLServerSocket class: " + ss.getClass());
			System.out.println("   String: " + ss.toString());

			// Generating the default SSLSocketFactory
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			out.println("\nSSLSocketFactory class: " + sf.getClass());
			dcsList = sf.getDefaultCipherSuites();
			out.println("   Default cipher suites:");
			for (int i = 0; i < dcsList.length; i++) {
				out.println("      " + dcsList[i]);
			}

			// Genearting SSLSocket
			SSLSocket s = (SSLSocket) sf.createSocket();
			System.out.println("\nSSLSocket class: " + s.getClass());
			System.out.println("   String: " + s.toString());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

}
