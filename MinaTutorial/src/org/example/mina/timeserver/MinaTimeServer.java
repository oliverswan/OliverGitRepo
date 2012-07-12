package org.example.mina.timeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.example.mina.timeserver.handler.TimeServerHandler;

/**
ThreadModel���Ƴ� .

ThreadModel�����������Ӧ��һ��Ԥ�ȶ����thread model��һ��IoService. 
However, configuring most thread models has turned out to be too simple to introduce any new construct. 
ThreadModeal caused a lot of confusion rather than ease of use. 

In 2.x, ������Լ���ʽ�����ExecutorFilter, ������Ҫ��ʱ��

ExecutorFilter maintains the order of events only with a certain Executor implementation.

In 1.x, ExecutorFilter��ά���¼���˳��������֪��ʲô����Executor . 
In 2.0, �ṩ�������µ�ThreadPoolExecutorʵ��; 

OrderedThreadPoolExecutor and UnorderedThreadPoolExecutor. 

ֻ�е���������£�ExecutorFilter�Ż�ά���¼���˳��:

it's constructed via a convenience constructor, which creates a new OrderedThreadPoolExecutor or 
OrderedThreadPoolExecutor ����ʽ��ָ��. 

OrderedThreadPoolExecutor and UnorderedThreadPoolExecutor 
also has prevention mechanism against OutOfMemoryError, 
�������������Executorʵ�֣����������������.

*/
public class MinaTimeServer {

	private static final int PORT = 9123;

	public static void main(String[] args) throws IOException {
		
		// 0. ����IO������
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		// 1. ������
		acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
	    acceptor.getFilterChain().addLast( "codec", 
	    		new ProtocolCodecFilter( 
	    				new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
	    
	    // 2. IO������
	    acceptor.setHandler(  new TimeServerHandler() );
	    
	    // 3. ��IO��������������
	    acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );

        // 4. �󶨶˿ڿ�ʼ����
        acceptor.bind( new InetSocketAddress(PORT) );

	}

}
