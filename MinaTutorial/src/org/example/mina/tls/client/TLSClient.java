package org.example.mina.tls.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.example.mina.tls.client.handler.TLSClientHandler;
import org.example.mina.tls.common.BogusSslContextFactory;

public class TLSClient {

	private static final int PORT = 50003;

	private static final String TARGET_IP = "127.0.0.1";

	public static void main(String[] args) throws GeneralSecurityException {
		/** �����ͻ��������� **/
		IoConnector connector = new NioSocketConnector();
		SslFilter connectorTLSFilter = new SslFilter(BogusSslContextFactory.getInstance(false));
		/** ����Ϊ�ͻ���ģʽ **/
		connectorTLSFilter.setUseClientMode(true);
		/** ���ü��ܹ����� **/
		connector.getFilterChain().addLast("SSL", connectorTLSFilter);
		/** �����¼������� **/
		connector.setHandler(new TLSClientHandler());
		/** ���ñ���������Ͱ��ж�ȡ����ģʽ **/
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		/** �������� **/
		ConnectFuture future = connector.connect(new InetSocketAddress(TARGET_IP, PORT));
		/** �ȴ����Ӵ������ **/
		future.awaitUninterruptibly();
		/** ��ȡ���ӻỰ **/
		IoSession session = future.getSession();
		/** ������Ϣ **/
		session.write("am i security?");//
		/** �ȴ����ӶϿ� **/
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
