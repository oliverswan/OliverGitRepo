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
ThreadModel被移除 .

ThreadModel起初是用来简化应用一个预先定义的thread model到一个IoService. 
However, configuring most thread models has turned out to be too simple to introduce any new construct. 
ThreadModeal caused a lot of confusion rather than ease of use. 

In 2.x, 你必须自己显式地添加ExecutorFilter, 当你需要的时候。

ExecutorFilter maintains the order of events only with a certain Executor implementation.

In 1.x, ExecutorFilter来维护事件的顺序，无论你知道什么样的Executor . 
In 2.0, 提供了两种新的ThreadPoolExecutor实现; 

OrderedThreadPoolExecutor and UnorderedThreadPoolExecutor. 

只有当下列情况下，ExecutorFilter才会维护事件的顺序:

it's constructed via a convenience constructor, which creates a new OrderedThreadPoolExecutor or 
OrderedThreadPoolExecutor 被显式地指定. 

OrderedThreadPoolExecutor and UnorderedThreadPoolExecutor 
also has prevention mechanism against OutOfMemoryError, 
所以相对于其他Executor实现，你会倾向于这两种.

*/
public class MinaTimeServer {

	private static final int PORT = 9123;

	public static void main(String[] args) throws IOException {
		
		// 0. 创建IO接收器
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		// 1. 过滤器
		acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
	    acceptor.getFilterChain().addLast( "codec", 
	    		new ProtocolCodecFilter( 
	    				new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
	    
	    // 2. IO处理器
	    acceptor.setHandler(  new TimeServerHandler() );
	    
	    // 3. 对IO接收器进行配置
	    acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );

        // 4. 绑定端口开始监听
        acceptor.bind( new InetSocketAddress(PORT) );

	}

}
