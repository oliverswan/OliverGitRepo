package net.oliver.j2se.security.jsse.demo1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

/**
JAVA 双向SSL，SOCKET客户端/服务端 

Server需要：
1）KeyStore: 其中保存服务端的私钥
2）Trust KeyStore:其中保存客户端的授权证书

Client需要：
1）KeyStore：其中保存客户端的私钥
2）Trust KeyStore：其中保存服务端的授权证书


使用Java自带的keytool命令，去生成这样信息文件：

1）生成服务端私钥，并且导入到服务端KeyStore文件中
2）根据私钥，导出服务端证书
3）将服务端证书，导入到客户端的Trust KeyStore中

采用同样的方法，生成客户端的私钥，客户端的证书，并且导入到服务端的Trust KeyStore中

1）keytool -genkey -alias clientkey -keystore kclient.keystore
2）keytool -export -alias clientkey -keystore kclient.keystore -file client.crt
3）keytool -import -alias clientkey -file client.crt -keystore tserver.keystore


启动Server
启动Client，发送信息。

Server接收如下：正确解密,返回Client信息，如此，就完成了服务端和客户端之间的基于身份认证的交互。

client采用kclient.keystore中的clientkey私钥进行数据加密，发送给server。

server采用tserver.keystore中的client.crt证书（包含了clientkey的公钥）对数据解密，如果解密成功，
证明消息来自client，进行逻辑处理。

server采用kserver.keystore中的serverkey私钥进行数据加密，发送给client。
client采用tclient.keystore中的server.crt证书（包含了serverkey的公钥）对数据解密，
如果解密成功，证明消息来自server，进行逻辑处理。

如果过程中，解密失败，那么证明消息来源错误。不进行逻辑处理。这样就完成了双向的身份认证。
*/


public class Server implements Runnable {

	private static final int DEFAULT_PORT = 7777;

	private static final String SERVER_KEY_STORE_PASSWORD = "123456";
	private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";

	private SSLServerSocket serverSocket;

	/**
	 * 启动程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.init();
		Thread thread = new Thread(server);
		thread.start();
	}

	public synchronized void start() {
		if (serverSocket == null) {
			System.out.println("ERROR");
			return;
		}
		while (true) {
			try {
				Socket s = serverSocket.accept();
				InputStream input = s.getInputStream();
				OutputStream output = s.getOutputStream();

				BufferedInputStream bis = new BufferedInputStream(input);
				BufferedOutputStream bos = new BufferedOutputStream(output);

				byte[] buffer = new byte[20];
				bis.read(buffer);
				System.out.println("------receive:--------"
						+ new String(buffer).toString());

				bos.write("yes".getBytes());
				bos.flush();

				s.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void init() {
		try {
			// 0.创建SSLContext
			SSLContext ctx = SSLContext.getInstance("SSL");
			
			// 1.创建密钥管理器工厂，用于产生密钥管理器(用来初始化SSLContext)
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

			// 2.保存服务端私钥
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream("src/net/oliver/j2se/security/jsse/demo1/agreeserver.jks"),SERVER_KEY_STORE_PASSWORD.toCharArray());
			
			// 3.保存客户端的公钥
			KeyStore tks = KeyStore.getInstance("JKS");
			tks.load(new FileInputStream("src/net/oliver/j2se/security/jsse/demo1/tagreeserver.jks"),SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

			// 4.初始化key管理器工厂
			kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
			tmf.init(tks);

			// 5.初始化SSLContext
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			// 6.创建服务端SSL Socket
			serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
			// 设置服务端也要验证客户端
			serverSocket.setNeedClientAuth(true);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void run() {
		start();
	}
}
