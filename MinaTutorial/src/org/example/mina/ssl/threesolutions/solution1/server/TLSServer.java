package org.example.mina.ssl.threesolutions.solution1.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.example.mina.ssl.threesolutions.solution1.BogusSslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TLSServer {

	private static final Logger logger = LoggerFactory.getLogger(TLSServer.class);
	private static final int PORT = 50003;

	public static void main(String[] args) throws Exception {
		//������������������
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);		
		
		//��ȡĬ�Ϲ�����
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		
		//���ü��ܹ����� 
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(true));
		//���ÿͻ�����ʱ��Ҫ��֤�ͻ���֤��
		sslFilter.setNeedClientAuth(true);		
		chain.addLast("sslFilter", sslFilter);
		
		//���ñ���������Ͱ��ж�ȡ����ģʽ
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		//�����¼�������
		acceptor.setHandler(new TLSServerHandler());
		
		//����󶨵��˶˿ں�		
		acceptor.bind(new InetSocketAddress(PORT));
		logger.debug("�������� [PORT] �ȴ�����...",PORT);
	}
}