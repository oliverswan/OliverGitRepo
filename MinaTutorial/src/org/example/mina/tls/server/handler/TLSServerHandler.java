package org.example.mina.tls.server.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TLSServerHandler extends IoHandlerAdapter {
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("[NIO Server]>> sessionCreated");
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
		System.out.println("[NIO Server Received]>> : "+(String) message);
		session.write("ssl is setup!");// 安全链接已建立
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Server]>> messageSent");
		System.out.println("[NIO Server messageSent]>> : " + (String) message);
	}
}