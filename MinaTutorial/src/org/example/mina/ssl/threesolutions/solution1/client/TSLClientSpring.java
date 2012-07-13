package org.example.mina.ssl.threesolutions.solution1.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TSLClientSpring {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = getApplicationContext();
		NioSocketConnector ioConnectorWithSSL = (NioSocketConnector) context.getBean("ioConnectorWithSSL");
		
		// ��������
		ConnectFuture future = ioConnectorWithSSL.connect(new InetSocketAddress("localhost", 50003));
		// �ȴ����Ӵ������
		future.awaitUninterruptibly();
		// ��ȡ���ӻỰ
		IoSession session = future.getSession();
		// ������Ϣ
		session.write("���ǰ�ȫ����?");
		// �ȴ����ӶϿ�
		session.getCloseFuture().awaitUninterruptibly();
		ioConnectorWithSSL.dispose();
	}

	public static ConfigurableApplicationContext getApplicationContext() {
		return new ClassPathXmlApplicationContext("com/sundoctor/mina/example1/ssl/client/clientContext.xml");
	}
}
