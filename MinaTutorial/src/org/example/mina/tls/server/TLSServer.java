package org.example.mina.tls.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.example.mina.tls.common.BogusSslContextFactory;
import org.example.mina.tls.server.handler.TLSServerHandler;


public class TLSServer {

	private static final int PORT = 50003;

	public static void main(String[] args) throws Exception {
		
		// 0. ����IO������
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);

		// 1. ���ù�����
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(true));// trueָ����server
		chain.addLast("sslFilter", sslFilter);// ����ssl����
		
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF-8"))));// ���ñ�����˺Ͱ��ж�ȡ

		// 2. ����IO������
		acceptor.setHandler(new TLSServerHandler());
		
		// 3. �󶨵��˿ڿ�ʼ���� 
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("�������� [" + PORT + "] �ȴ�����...");
	}
}