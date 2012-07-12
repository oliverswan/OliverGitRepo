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
		
		// 0. 创建IO接收器
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);

		// 1. 设置过滤器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(true));// true指明是server
		chain.addLast("sslFilter", sslFilter);// 设置ssl过滤
		
		chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName("UTF-8"))));// 设置编码过滤和按行读取

		// 2. 设置IO处理器
		acceptor.setHandler(new TLSServerHandler());
		
		// 3. 绑定到端口开始监听 
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("服务器在 [" + PORT + "] 等待连接...");
	}
}