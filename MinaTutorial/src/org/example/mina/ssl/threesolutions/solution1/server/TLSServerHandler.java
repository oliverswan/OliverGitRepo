package org.example.mina.ssl.threesolutions.solution1.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TLSServerHandler extends IoHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(TLSServerHandler.class);

	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("[NIO Server]>> sessionCreated");
	}

	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("[NIO Server]>> sessionOpened");
	}

	public void sessionClosed(IoSession session) throws Exception {
		logger.debug("[NIO Server]>> sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("[NIO Server]>> sessionIdle");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.debug("[NIO Server]>> exceptionCaught :");
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.debug("[NIO Server]>> messageReceived");
		logger.debug("[NIO Server Received]>> : {}", (String) message);
		session.write("安全链接已建立!");
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		logger.debug("[NIO Server]>> messageSent");
		logger.debug("[NIO Server messageSent]>> : {}", (String) message);
	}
}