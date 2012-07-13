package org.example.mina.tls.client.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;
import org.example.mina.tls.common.BogusSslContextFactory;

public class TempTLSClientHandler extends IoHandlerAdapter {
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("[NIO Client]>> sessionCreated");
	}

	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("[NIO Client]>> sessionOpened");
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("[NIO Client]>> sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("[NIO Client]>> sessionIdle");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("[NIO Client]>> exceptionCaught :");
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Client]>> messageReceived");
		System.out.println("[NIO Client Received]>>" + (String) message);
		if ("Hello SSL".equals((String) message)) {
			session.write("Client SSL Finished");
		} else if ("Server SSL Finished".equals((String) message)) {
			session.write("信息安全吗?");
		} else if ("信息安全!".equals((String) message)) {
			session.write("lawless command");
		}
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Client]>> messageSent");
		System.out.println("[NIO Client messageSent]>> : " + (String) message);
		if ("Client SSL Finished".equals((String) message)) {
			SslFilter connectorTLSFilter = new SslFilter(BogusSslContextFactory.getInstance(false));
			connectorTLSFilter.setUseClientMode(true);
			session.getFilterChain().addFirst("SSL", connectorTLSFilter);
			// (SslFilter)session.getFilterChain().get("SSL")).startSsl(session);
		}
	}
}
