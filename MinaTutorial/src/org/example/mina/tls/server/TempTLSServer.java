package org.example.mina.tls.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.example.mina.tls.server.handler.TempTLSServerHandler;

public class TempTLSServer {
	private static final int PORT = 50003;

	public static void main(String[] args) throws Exception {
		/** 创建服务器端连接器 **/
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		/** 获取默认过滤器 **/
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		/** 设置编码过滤器和按行读取数据模式 **/
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		/** 设置事件处理器 **/
		acceptor.setHandler(new TempTLSServerHandler());
		/** 服务绑定到此端口号 **/
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("服务器在 [" + PORT + "] 等待连接...");
	}
}
