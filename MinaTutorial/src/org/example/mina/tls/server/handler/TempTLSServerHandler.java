package org.example.mina.tls.server.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.filter.ssl.SslFilter.SslFilterMessage;
import org.example.mina.tls.common.BogusSslContextFactory;

public class TempTLSServerHandler extends IoHandlerAdapter {
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("[NIO Server]>> sessionCreated");
		/** 启用加密过滤通知 **/
		session.setAttribute(SslFilter.USE_NOTIFICATION);
	}

	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("[NIO Server]>> sessionOpened");
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("[NIO Server]>> sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("[NIO Server]>> sessionIdle");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("[NIO Server]>> exceptionCaught :");
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Server]>> messageReceived");
		String msg = "";
		if (message instanceof SslFilterMessage) {
			msg = ((SslFilterMessage) message).toString();
		} else {
			msg = (String) message;
		}
		System.out.println("[NIO Server Received]>> : " + msg);
		if ("Hello".equalsIgnoreCase(msg)) {
			session.write("Hello SSL");
		} else if ("Client SSL Finished".equalsIgnoreCase(msg)) {
			session.getFilterChain().addFirst("SSL", new SslFilter(BogusSslContextFactory.getInstance(true)));
			// (SslFilter)session.getFilterChain().get("SSL")).startSsl(session);
			session.write("Server SSL Finished");
		} else if ("信息安全吗?".equals(msg)) {
			session.write("信息安全!");
		} else {
			session.write("No Support Command");
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Server]>> messageSent");
		System.out.println("[NIO Server messageSent]>> : " + (String) message);
	}

}
