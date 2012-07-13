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
		/** 创建客户端连接器 **/
		IoConnector connector = new NioSocketConnector();
		SslFilter connectorTLSFilter = new SslFilter(BogusSslContextFactory.getInstance(false));
		/** 设置为客户端模式 **/
		connectorTLSFilter.setUseClientMode(true);
		/** 设置加密过滤器 **/
		connector.getFilterChain().addLast("SSL", connectorTLSFilter);
		/** 设置事件处理器 **/
		connector.setHandler(new TLSClientHandler());
		/** 设置编码过滤器和按行读取数据模式 **/
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		/** 创建连接 **/
		ConnectFuture future = connector.connect(new InetSocketAddress(TARGET_IP, PORT));
		/** 等待连接创建完成 **/
		future.awaitUninterruptibly();
		/** 获取连接会话 **/
		IoSession session = future.getSession();
		/** 发送信息 **/
		session.write("am i security?");//
		/** 等待连接断开 **/
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
