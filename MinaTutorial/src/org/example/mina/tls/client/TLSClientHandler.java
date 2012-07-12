package org.example.mina.tls.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TLSClientHandler extends IoHandlerAdapter {
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
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("[NIO Client]>> messageSent");
		System.out.println("[NIO Client messageSent]>> : " + (String) message);
	}
}
